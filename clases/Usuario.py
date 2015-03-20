#!/usr/bin/python
# -*- coding: utf-8 -*-

from Projecto import Projecto
#from Projecto import Projecto

class Usuario :
	
	#Constructor segun posicion color y tipo
	def __init__ (self, name, nick, passwd, correo, descripcion, pais, localidad) :
		self.name = name
		self.nick = nick
		self.passwd = passwd
		self.correo = correo
		self.descripcion = descripcion
		self.pais = pais
		self.localidad = localidad
		self.userPro = []
		self.otherPro = []
		self.tags = []
	

	#Funciones de gestion de projectos

	def addUserPro(self, projecto) :
		self.userPro.append(projecto)
	
	def addOtherPro(self, projecto) :
		self.otherPro.append(projecto)

	def removeUserPro(self, projecto) :
		self.userPro.remove(projecto)

	def removeOtherPro(self, projecto) :
		self.otherPro.remove(projecto)
				

	#Getters y setters

	def getName(self) :
		return self.name

	def getUserPro(self) :
		return self.userPro

	def getPasswd(self) :
		return self.passwd

	def getOtherPro(self) :
		return self.otherPro

	def getNick(self) :
		return self.nick

	def getCorreo(self) :
		return self.correo

	def getDescripcion(self) :
		return self.descripcion

	def getPais(self) :
		return self.pais
	
	def getLocalidad(self) :
		return self.localidad
	
	def addTag (self, tag) :
		self.tags.append(tag)

	def removeTag(self, tag) :
		self.tags.remove(tag)

	def getTags(self) :
		return self.tags

	def getJsonResponse(self) :
		dicc = {}
		dicc["name"] = self.name
		dicc["nick"] = self.nick
		dicc["correo"] = self.correo
		dicc["descripcion"] = self.descripcion
		dicc["pais"] = self.pais
		dicc["localidad"] = self.localidad

		aux = []
		for project in self.userPro :
			aux2 = {}
			aux2["nombre"] = project.getNombre()
			aux.append(aux2)

		dicc["propios"] = aux

		aux = []
		for project in self.otherPro :
			aux2 = {}
			aux2["nombre"] = project.getNombre()
			aux.append(aux2)

		dicc["otros"] = aux

		aux = []
		for tag in self.tags :
			aux2 = {}
			aux2["tag"] = tag
			aux.append(aux2)

		dicc["tags"] = aux

		return dicc
