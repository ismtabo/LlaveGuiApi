#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
import web
import json
# import clases
import persistencia

# Constantes:
FORMERROR = 'Error en el formulario'
ALREADYEXISTS = 'Usuario ya existente con ese nick'

render = web.template.render('templates/')
# controladora = clases.Controlador()
controladora = persistencia.ControladorDB()

# urls = (
# 	'/registro/', 		'Registro',
# 	'/login/', 			'Login',
# 	'/getUsuario/', 	'GetUsuario',
# 	'/getAllUsr/', 		'GetAllUsr',
# 	'/setUsrImagen/', 	'SetUsrImagen',
# 	'/getUsrImagen/', 	'GetUsrImagen',
# )
urls = (
	'/','index',
	'/login/', 'Login',
	'/take/', 'setKeyUser',
	'/drop/', 'resetKeyUser',
	'/who/', 'getUserKey'
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
class index:
	# TODO --Revisar--

	def getform(self):
		return web.form.Form(
	        web.form.Textbox('nick', web.form.notnull, 
	            description="Nick:"),
	        web.form.Password('passwd', web.form.notnull, 
	            description="Password:"),
	        web.form.Button('Submit'),
	    )

	def GET(self):
		print 'llega a GET'
		""" Show page """
		form = self.getform()
		print 'creamos formulario'
		return render.index(form,None)

	def POST(self):
		""" Add new entry """
		form = self.getform()
		if not form.validates():
			return render.index(form,FORMERROR)
		result = controladora.newUser(form.d.nick,form.d.passwd)
		return render.index(form,ALREADYEXISTS if not result else None)

class Login :
	def OPTIONS (self) :
		web.header('Access-Control-Allow-Origin',      '*')
		web.header('Access-Control-Allow-Credentials', 'true')
		web.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
		web.header("Access-Control-Allow-Methods", "GET, PUT, POST")
		a = None

	def POST (self) :
		# TODO --Falta de implementación--
		jobj = json.loads(web.data())
		dicc = {}

		if controladora.login(jobj) == True :
			dicc["resultado"] = True
		else :
			dicc["resultado"] = False

		web.header('Content-Type', 'application/json')
		web.header('Access-Control-Allow-Origin',      '*')
		web.header('Access-Control-Allow-Credentials', 'true')
		web.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
		web.header("Access-Control-Allow-Methods", "GET, PUT, POST")

		print jobj
		print dicc
		return json.dumps(dicc)

class setKeyUser :
	def OPTIONS (self) :
		web.header('Access-Control-Allow-Origin',      '*')
		web.header('Access-Control-Allow-Credentials', 'true')
		web.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
		web.header("Access-Control-Allow-Methods", "GET, PUT, POST")

	def POST (self) :
		jobj = json.loads(web.data())
		web.header('Content-Type', 'application/json')
		web.header('Access-Control-Allow-Origin',      '*')
		web.header('Access-Control-Allow-Credentials', 'true')
		web.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
		web.header("Access-Control-Allow-Methods", "GET, PUT, POST")

		jobj = json.loads(web.data())
		if controladora.login(jobj) == False or controladora.isKeyUser():
			dicc = {'resultado':False}
			return json.dumps(dicc)
		else:
			controladora.setKeyUser(jobj)
			dicc = {'resultado':True}
			return json.dumps(dicc)
			

class resetKeyUser :
	def OPTIONS (self) :
		web.header('Access-Control-Allow-Origin',      '*')
		web.header('Access-Control-Allow-Credentials', 'true')
		web.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
		web.header("Access-Control-Allow-Methods", "GET, PUT, POST")

	def POST (self) :
		jobj = json.loads(web.data())
		web.header('Content-Type', 'application/json')
		web.header('Access-Control-Allow-Origin',      '*')
		web.header('Access-Control-Allow-Credentials', 'true')
		web.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
		web.header("Access-Control-Allow-Methods", "GET, PUT, POST")

		if controladora.login(jobj) == False :
			dicc = {'resultado':False}
			return json.dumps(dicc)
		if controladora.sameUser(jobj):
			controladora.setKeyUser(None)
			dicc = {'resultado':True}
			return json.dumps(dicc)
		else :
			dicc = {'resultado':False}
			return json.dumps(dicc)

class getUserKey :
	def OPTIONS (self) :
		web.header('Access-Control-Allow-Origin',      '*')
		web.header('Access-Control-Allow-Credentials', 'true')
		web.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
		web.header("Access-Control-Allow-Methods", "GET, PUT, POST")

	def POST(self) :
		web.header('Content-Type', 'application/json')
		web.header('Access-Control-Allow-Origin',      '*')
		web.header('Access-Control-Allow-Credentials', 'true')
		web.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
		web.header("Access-Control-Allow-Methods", "GET, PUT, POST")

		dicc = {"nick":controladora.getKeyUser()}
		return json.dumps(dicc)



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
