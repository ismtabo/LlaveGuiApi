#!/usr/bin/python
# -*- coding: utf-8 -*-

class Usuario :
	
	#Constructor segun posicion color y tipo
	def __init__ (self, nick, passwd="tonto123") :
		self.nick = nick
		self.passwd = passwd
	

	#Getters y setters

	def getPasswd(self) :
		return self.passwd

	def getNick(self) :
		return self.nick

	def getJsonResponse(self) :
		dicc = {}
		dicc["name"] = self.name
		dicc["nick"] = self.nick

		return dicc
