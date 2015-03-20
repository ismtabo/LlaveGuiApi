#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
import web
import json
import clases

controladora = clases.Controlador()

urls = (
	'/registro/', 		'Registro',
	'/login/', 			'Login',
	'/nuevoPro/', 		'NuevoPro',
	'/unirsePro/', 		'UnirsePro',
	'/borrarPro/', 		'BorrarPro',
	'/getUsuario/', 	'GetUsuario',
	'/getProjecto/', 	'GetProjecto',
	'/getAllUsr/', 		'GetAllUsr',
	'/getAllPro/', 		'GetAllPro',
	'/setUsrImagen/', 	'SetUsrImagen',
	'/setProImagen/', 	'setProImagen',
	'/getUsrImagen/', 	'GetUsrImagen',
	'/getProImagen/', 	'GetProImagen'
)

app = web.application(urls, globals())

class Registro :
	def POST (self) :
		jobj = json.loads(web.data())
		dicc = {}
		aux = controladora.nuevoUser(jobj)
		if aux[0]  == True:
			dicc["resultado"] = True
		else :
			dicc["resultado"] = False
			dicc["error"] = aux[1]

		return json.dumps(dicc)

class Login :
	def POST (self) :
		jobj = json.loads(web.data())
		dicc = {}

		if controladora.login(jobj) == True :
			dicc["resultado"] = True
		else :
			dicc["resultado"] = False

		print jobj
		print dicc
		return json.dumps(dicc)

class NuevoPro :
	def POST (self) :
		jobj = json.loads(web.data())
		dicc = {}

		if controladora.nuevoProjecto(jobj) == True :
			dicc["resultado"] = True
		else :
			dicc["resultado"] = False

		return json.dumps(dicc)

class UnirsePro :
	def POST (self) :
		jobj = json.loads(web.data())
		controladora.addUserToProject(jobj)

class BorrarPro :
	def POST (self) :
		jobj = json.loads(web.data())
		controladora.removeProject(jobj)

class GetUsuario :
	def POST (self) :
		jobj = json.loads(web.data())
		return json.dumps(controladora.getUserInfo(jobj))

class GetProjecto :
	def POST (self) :
		jobj = json.loads(web.data())
		return json.dumps(controladora.getProjectInfo(jobj))

class GetAllUsr :
	def POST (self) :
		return json.dumps(controladora.getAllUserNick())

class GetAllPro :
	def POST (self) :
		return json.dumps(controladora.getAllProjectNombre())

class SetUsrImagen :
	def POST (self) :
		jobj = json.loads(web.data())
		controladora.setImagenUsuario(jobj)

class SetProImagen :
	def POST (self) :
		jobj = json.loads(web.data())
		controladora.setImagenProjecto(jobj)

class GetUsrImagen :
	def POST (self) :
		jobj = json.loads(web.data())
		web.header("Content-Type", "png")
		return controladora.getImagenUsuario(jobj)

class GetProImagen :
	def POST (self) :
		jobj = json.loads(web.data())
		web.header("Content-Type", "png")
		return controladora.getImagenProjecto(jobj)

if __name__ == "__main__":
	app.run()
