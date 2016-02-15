#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
Api para la gestión por parte del servidor de la aplicación de la llave del GUI.
El principal objetivo es mantener constancia del usuario que tenga la llave,
y un sistema de logeo para acceder correctamente al uso de la aplicación mediante una base
de datos proporcionada por el grupo.
"""

import sys
import web
import json
import persistencia
import os

# Configuración del debug de la aplicación
web.config.debug = False

# Constantes:
FORMERROR = 'Error en el formulario'
ALREADYEXISTS = 'Usuario ya existente con ese nick'
ALLOK = 'Usuario registrado'

render = web.template.render('templates/')
controladora = persistencia.ControladorDB()

urls = (
	'/','index',
	# Key Users System
	'/login/', 'Login',
	'/take/', 'setKeyUser',
	'/state/', 'setKeyState',
	'/drop/', 'resetKeyUser',
	'/who/', 'getUserKey',
	# Admin System
	'/adminlog/', 'adminLogin',
	'/users/', 'getAllUsers',
	'/images/(.*)', 'getImages',
	'/.*/css/(.*).css','getCss'
	)

app = web.application(urls, globals())
vuser = web.form.regexp('^(\w+)$','Caracter no alfa numérico en campo dde usuario.')

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
	        web.form.Textbox('nick',vuser, web.form.notnull, 
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
		return render.index(form,ALREADYEXISTS if not result else ALLOK)

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

		dicc = {"nick":controladora.getKeyUser(),"estado":controladora.getKeyState()}
		return json.dumps(dicc)

class setKeyState:
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

		jobj = json.loads(web.data())
		if controladora.login(jobj) == False or not controladora.isKeyUser():
			dicc = {'resultado':False}
			return json.dumps(dicc)
		else:
			resultado = controladora.setKeyState(jobj)
			dicc = {'resultado':resultado}
			return json.dumps(dicc)

class getAllUsers:

	def GET(self):
		""" Show page """
		return render.users(controladora.getAll())


"""
Otras opciones básicas de obtención de estilos e imagenes
"""
class getImages:
    def GET(self,name):
        ext = name.split(".")[-1] # Gather extension

        cType = {
            "png":"images/png",
            "jpg":"images/jpeg",
            "jpeg":"images/jpeg",
            "gif":"images/gif",
            "ico":"images/x-icon"            }

        print os.listdir('images/%s'%ext)
        print name in os.listdir('images/%s'%ext)
        if name in os.listdir('images/%s'%ext):  # Security
            web.header("Content-Type", cType[ext]) # Set the Header
            return open('images/%s/%s'%(ext,name),"rb").read() # Notice 'rb' for reading images
        else:
            raise web.notfound()

class getCss:
    def GET(self,name):
    	name += '.css'
    	print 'Hola css',name
        if name in os.listdir('templates/css'):  # Security
            web.header("Content-Type", 'css') # Set the Header
            return open('templates/css/%s'%name,"rb").read() # Notice 'rb' for reading images
        else:
            raise web.notfound()

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
