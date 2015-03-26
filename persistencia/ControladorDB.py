#!/usr/bin/python
# -*- coding: utf-8 -*-

import json
import web
import config
import re
from DBApi import DBApi

DEFAULT = 'secretaria'
REGEXP = r'(\w+)$'

state_range = [0,1,2,3,4,5]


class ControladorDB:
	"""
	Implementación de Controlador de Usuario, correspondiente a una persistencia de DB.
	El acceso a una DB se hace desde un controlador dbApi, quien hace de intermediaria para
	tomar los datos y convertirlo en objetos.
	"""
	def __init__(self):
		self.dbApi = DBApi()
		self.keyUser = DEFAULT
		self.keyState = 0
		self.pattern = re.compile('^(\w+)$')

	def login(self,jobt):
		match = self.pattern.match(jobt['nick'])
		if match == None:
			return False
		usr = self.dbApi.getUserByNick(jobt['nick'])
		# TODO --Falta de implementación--
		try:
			if not usr:
				return False
			if usr.getPasswd() == jobt['passwd']:
				return True
			else:
				return False
		except:
			return False

	def setKeyUser(self,jobt):
		if jobt:
			self.keyUser = jobt['nick']
		else :
			self.keyUser = DEFAULT
		self.keyState = 0

	def getKeyUser(self):
		return self.keyUser
	
	def isKeyUser(self):
		return not self.keyUser == DEFAULT

	def sameUser(self,jobj):
		return self.keyUser == jobj['nick']

	def newUser(self,nick,passwd):
		# TODO --Revisar--
		return self.dbApi.newUser(nick,passwd)

	def setKeyState(self,jobj):
		if jobj['nick'] != self.keyUser:
			return False
		if not isinstance(jobj['estado'],int) or not jobj['estado'] in state_range:
			return False
		else :
			self.keyState = jobj['estado']
			return True

	def getKeyState(self):
		return self.keyState
