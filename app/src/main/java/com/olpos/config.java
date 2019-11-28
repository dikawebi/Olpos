package com.olpos;

public class config {

    public static final String URL_ADD="http://localhost/Android/insert.php";
    public static final String URL_GET_ALL = "http://localhost/Android/select.php";
    public static final String URL_GET_EMP = "http://localhost/Android/select.php?id=";
    public static final String URL_UPDATE_EMP = "http://localhost/Android/update.php";
    public static final String URL_DELETE_EMP = "http://localhost/Android/delete.php?id=";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID = "id";
    public static final String KEY_EMP_USERID = "userid";
    public static final String KEY_EMP_PASSWORD = "password"; //desg itu variabel untuk posisi
    public static final String KEY_EMP_LEVEL = "level"; //salary itu variabel untuk gajih

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_USERID = "userid";
    public static final String TAG_PASSWORD = "password";
    public static final String TAG_LEVEL = "level";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
}
