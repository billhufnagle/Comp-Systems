import random as rd
import hashlib


#hashval=hashlib.sha1(s).hexdigest()

def shabirthday():
    dicenc = {}
    count = 0
    b= ''
    while(True and count<30000000):
        b=''
        for i in range(90):
            b=b + chr (rd.randint(0, 256))
        #print (b)
        #b=hex(int(b))
        benc= b.encode()
        hashvalb=hashlib.sha1(benc).hexdigest()
        count= count+1
        try:
            if (not(dicenc[hashvalb[0:11]]==None)):
                break
        except KeyError:
            dicenc[hashvalb[0:11]] = b
        if (count%100000==0):
            print (count)
            print (len(dicenc))
    return (dicenc[hashvalb[0:11]],b, count)
x=shabirthday()
print(x)
print(hashlib.sha1(x[0].encode()).hexdigest())
print(hashlib.sha1(x[1].encode()).hexdigest())
