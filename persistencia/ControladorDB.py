#!/usr/bin/python
# -*- coding: utf-8 -*-

import clases
import json
import web
import config


class ControladorDB:
	"""
	Implementación de Controlador de Usuario, correspondiente a una persistencia de DB.
	El acceso a una DB se hace desde un controlador dbApi, quien hace de intermediaria para
	tomar los datos y convertirlo en objetos.
	"""
	def __init__(self):
		self.dbApi = DBApi()

	def login(self,jobt):
		usr = self.dbApi.getUserByNick(jobt['nick'])
		# TODO --Falta de implementación--
		# try:
		# 	if 

	def setKeyUser(self,jobt):
