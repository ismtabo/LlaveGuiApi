all :
	nohup python apiByt.py 8080 &
clean : 
	rm  clases/*.pyc
	rm *.pyc
