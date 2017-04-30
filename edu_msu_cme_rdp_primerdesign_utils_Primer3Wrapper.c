#include "edu_msu_cme_rdp_primerdesign_utils_Primer3Wrapper.h"
#include "oligotm.h"
#include "thal.h"
#include <unistd.h>
#include <string.h>

JNIEXPORT void JNICALL Java_edu_msu_cme_rdp_primerdesign_utils_Primer3Wrapper_initThermoPath(JNIEnv *env, jclass obj, jstring path) {
  thal_results o;
  char *configpath = (char *)(*env)->GetStringUTFChars(env, path, 0);
  strcat(configpath,"/primer3_config/");

  //printf("%s\n",configpath);
  int i = get_thermodynamic_values("/home/gunturus/PrimerDesign/jni/primer3_config/",&o);
  //printf("%s\n",o.msg);
}

JNIEXPORT jdouble JNICALL Java_edu_msu_cme_rdp_primerdesign_utils_Primer3Wrapper_calcTm(JNIEnv *env, jobject obj, jstring seq, jdouble d, jdouble mv, jdouble dv, jdouble dntp, jint tm_method, jint salt_method) {
  
  const char *sequence = (*env)->GetStringUTFChars(env, seq, 0);
  tm_method_type tm = tm_method;
  salt_correction_type salt = salt_method;

  double ret = oligotm(sequence, d, mv, dv, dntp, tm, salt);
  
  (*env)->ReleaseStringUTFChars(env, seq, sequence);

  return ret; 
}

JNIEXPORT jdouble JNICALL Java_edu_msu_cme_rdp_primerdesign_utils_Primer3Wrapper_calcThermo(JNIEnv *env, jobject obj, jstring seq1, jstring seq2, jint maxloop, jdouble mv, jdouble dv, jdouble dntp, jdouble dna_conc, jdouble temp, jint temponly, jint dimer, jint aligntype) {

  
  const char *sequence1 = (*env)->GetStringUTFChars(env,seq1,0);
  const char *sequence2 = (*env)->GetStringUTFChars(env,seq2,0);
  
  thal_args a;
  
  a.debug = 0;
  a.type = (thal_alignment_type) aligntype;
  a.maxLoop = maxloop;
  a.mv = mv;
  a.dv = dv;
  a.dntp = dntp;
  a.dna_conc = dna_conc;
  a.temp = temp;
  a.temponly = temponly;
  a.dimer = dimer;
  
  thal_results o;

  thal(sequence1, sequence2, &a, &o);
  
  (*env)->ReleaseStringUTFChars(env, seq1, sequence1);
  (*env)->ReleaseStringUTFChars(env, seq2, sequence2);
  /*
  printf("%i\n",a.type);
  printf("%i\n",a.maxLoop);
  printf("%s\n",o.msg);
  printf("%f\n",o.temp);
  printf("%s\n",sequence1);
  printf("%s\n",sequence2);
  printf("%f\n",a.temp);
  printf("%f\n",a.mv);
  printf("%f\n",a.dv);
  printf("%f\n",a.dntp);
  printf("%f\n",a.dna_conc);
  printf("%i\n",a.temponly);
  printf("%i\n",a.dimer);
  */

  return o.temp;
}
