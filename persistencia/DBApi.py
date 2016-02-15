# -*- coding: utf-8 -*-

import web
from config import *
import itertools
import sqlite3 as lite
from Usuario import Usuario
import os

LOCALCWD = os.getcwd()

NICK = 0
PASSWD = 1

class DBApi :
	"""
	Implementación de Api Rest contra base de datos, tomando los valores de acceso desde config.py.
	"""
	def __init__(self):
		# Acceso a BD Mysql
		# self.db = web.database(dbn=DBNETWORK, user=DBUSER, pw=DBPASSWD, db=DBNAME)

		# Acceso a BD SQLite
		# self.db = lite.connect(LOCALCWD+'/persistencia/keydb.db')
		self.a = None
	def getUserByNick(self,nick):
		"""
		De una consulta a la base de datos devuelve una instancia de Usuario correspondiente al id.
		"""
		db = lite.connect(LOCALCWD+'/persistencia/keydb.db')
		c = db.cursor()

		row = c.execute('SELECT nick,passwd FROM users WHERE nick="{}"'.format(nick)).fetchone()
		db.close()
		return None if not row else Usuario(row[NICK],row[PASSWD])

	def newUser(self,nick,passwd):
		# TODO --Revisar--
		"""
		Introduce un nuevo usuario dentro de la base de datos
		"""
		db = lite.connect(LOCALCWD+'/persistencia/keydb.db')
		c = db.cursor()
		row = c.execute('SELECT nick,passwd FROM users WHERE nick="{}"'.format(nick)).fetchone()
		if not row:
			row = c.execute('INSERT INTO users values ("{}","{}")'.format(nick,passwd))
			db.commit()
			db.close()
			return True
		else:
			db.close()
			return False

	def getAll(self):
		db = lite.connect(LOCALCWD+'/persistencia/keydb.db')
		c = db.cursor()
		row = c.execute('SELECT nick FROM users').fetchall()
		return map(lambda x: Usuario(x[0]),row)
