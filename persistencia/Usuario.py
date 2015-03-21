#!/usr/bin/python
# -*- coding: utf-8 -*-

from Projecto import Projecto
#from Projecto import Projecto

class Usuario :
	
	#Constructor segun posicion color y tipo
	def __init__ (self, name, nick, passwd, rol) :
		self.name = name
		self.nick = nick
		self.passwd = passwd
		self.rol = rol
	

	#Getters y setters

	def getName(self) :
		return self.name

	def getPasswd(self) :
		return self.passwd

	def getNick(self) :
		return self.nick

	def getJsonResponse(self) :
		dicc = {}
		dicc["name"] = self.name
		dicc["nick"] = self.nick

		return dicc
