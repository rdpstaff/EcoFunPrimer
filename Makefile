include config

UNAME := $(shell uname)
ifeq ($(UNAME), Darwin)
	libt=-dynamiclib
else
	libt=-shared
endif
copy: libPrimer3.so
	-(cp libPrimer3.so dist/)
libPrimer3.so: thal.o oligotm.o
	gcc $(libt) -o $@ edu_msu_cme_rdp_primerdesign_utils_Primer3Wrapper.c thal.o oligotm.o -I$(java_jni_include) -I$(java_jni_include_os) -I. -lm
		#gcc -shared -fPIC -o $@ edu_msu_cme_rdp_primerdesign_utils_Primer3Wrapper.c thal.o oligotm.o -I$(java_jni_include) -I$(java_jni_include_os) -I. -lm
thal.o:
	gcc -c -Wall -fPIC -o $@ thal.c

oligotm.o:
	gcc -c -Wall -fPIC -o $@ oligotm.c -lm

clean:
	rm *.o
	rm *.so
