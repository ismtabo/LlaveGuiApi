#!/usr/bin/python
# -*- coding: utf-8 -*-

import json
import web
import config
import re
from DBApi import DBApi

DEFAULT = 'secretaria'
REGEXP = r'(\w+)$'


class ControladorDB:
	"""
	Implementación de Controlador de Usuario, correspondiente a una persistencia de DB.
	El acceso a una DB se hace desde un controlador dbApi, quien hace de intermediaria para
	tomar los datos y convertirlo en objetos.
	"""
	def __init__(self):
		self.dbApi = DBApi()
		self.keyUser = DEFAULT
		self.pattern = re.compile('^(\w+)$')

	def login(self,jobt):
		match = self.pattern.match(jobt['nick'])
		print match
		if match == None:
			print 'ey you modafuka'
			return False
		usr = self.dbApi.getUserByNick(jobt['nick'])
		# TODO --Falta de implementación--
		try:
			print usr.getPasswd(),'-',jobt['passwd']
			if not usr:
				return False
			if usr.getPasswd() == jobt['passwd']:
				return True
			else:
				return False
		except:
			print 'Algo salió mal'
			return False

	def setKeyUser(self,jobt):
		if jobt:
			self.keyUser = jobt['nick']
		else :
			self.keyUser = DEFAULT
		print 'Nuevo usuario con la llave',self.keyUser

	def getKeyUser(self):
		print 'La tiene',self.keyUser
		return self.keyUser
	
	def isKeyUser(self):
		return not self.keyUser == DEFAULT

	def sameUser(self,jobj):
		return self.keyUser == jobj['nick']

	def newUser(self,nick,passwd):
		# TODO --Revisar--
		return self.dbApi.newUser(nick,passwd)
