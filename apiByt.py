#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
import web
import json
import clases

controladora = clases.Controlador()

# urls = (
# 	'/registro/', 		'Registro',
# 	'/login/', 			'Login',
# 	'/getUsuario/', 	'GetUsuario',
# 	'/getAllUsr/', 		'GetAllUsr',
# 	'/setUsrImagen/', 	'SetUsrImagen',
# 	'/getUsrImagen/', 	'GetUsrImagen',
# )
urls = (
	'/login/', 'Login',
	'/takeKey/', 'setKeyUser',
	'/leaveKey/', 'setKeyUser'
	)

app = web.application(urls, globals())

# class Registro :
# 	def POST (self) :
# 		jobj = json.loads(web.data())
# 		dicc = {}
# 		aux = controladora.nuevoUser(jobj)
# 		if aux[0]  == True:
# 			dicc["resultado"] = True
# 		else :
# 			dicc["resultado"] = False
# 			dicc["error"] = aux[1]

# 		return json.dumps(dicc)

class Login :
	def POST (self) :
		# TODO --Falta de implementación--
		jobj = json.loads(web.data())
		dicc = {}

		if controladora.login(jobj) == True :
			dicc["resultado"] = True
		else :
			dicc["resultado"] = False

		print jobj
		print dicc
		return json.dumps(dicc)

class setKeyUser :
	def POST (self) :
		jobj = json.loads(web.data())
		controladora.setKeyUser(jobj)

# class GetUsuario :
# 	def POST (self) :
# 		jobj = json.loads(web.data())
# 		return json.dumps(controladora.getUserInfo(jobj))

# class GetAllUsr :
# 	def POST (self) :
# 		return json.dumps(controladora.getAllUserNick())

# class SetUsrImagen :
# 	def POST (self) :
# 		jobj = json.loads(web.data())
# 		controladora.setImagenUsuario(jobj)

# class GetUsrImagen :
# 	def POST (self) :
# 		jobj = json.loads(web.data())
# 		web.header("Content-Type", "png")
# 		return controladora.getImagenUsuario(jobj)

if __name__ == "__main__":
	app.run()
