#!/usr/bin/python
# -*- coding: utf-8 -*-

import web
from config import *

class DBApi :
	"""
	Implementación de Api Rest contra base de datos, tomando los valores de acceso desde config.py.
	"""
	def __init__(self):
		self.db = web.database(dbn=DBNETWORK, user=DBUSER, pw=DBPASSWD, db=DBNAME)

	def getUserById(self,id):
		"""
		De una consulta a la base de datos devuelve una instancia de Usuario correspondiente al id.
		"""
		userList = db.select('users',where='id = '+id).list()
		# TODO --Falta de implementación--
		return None
