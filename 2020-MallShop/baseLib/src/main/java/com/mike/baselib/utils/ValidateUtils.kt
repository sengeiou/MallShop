package com.mike.baselib.utils

import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException


class ValidateUtils{

     companion object {

         fun validateEmail(email:String):Boolean{
            return validateStr(email,"^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})\$")
         }
         fun validatePhoneNo(phone:String):Boolean{
             //return true
            return validateStr(phone,"^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}\$") || validateStr(phone,"^([6|9])\\d{7}\$")
         }
         fun validateIdcard(idcard:String):Boolean{
             return true
            // return validateStr(idcard,"^(\\d{15}\$|^\\d{18}\$|^\\d{17}(\\d|X|x))\$")
         }
         fun validatePassword(password:String):Boolean{
             //长度在8~16之间，只能包含字母、数字
            // return true
            return validateStr(password,"^(?![^a-zA-Z]+\$)(?!\\\\D+\$).{8,16}\$")
         }
         fun validateVfcode(vfcode:String):Boolean{
             return true
            //return validateStr(vfcode,"^\\d{6}\$")
         }

         private fun validateStr(str:String,regexStr: String):Boolean{
             val regex = Regex(regexStr)
             return regex.matches(str)
         }

         /**
          * 限制特殊字符输入
          * @param str
          * @return
          * @throws PatternSyntaxException
          */
         @Throws(PatternSyntaxException::class)
         fun stringFilter(str: String): Boolean {
             val regex = "[\\\\`~!@#$%^&*+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*——+|{}【】‘；：”“’。，、？]"
             val pattern = Pattern.compile(regex)
             val matcher = pattern.matcher(str)
             //return matcher.replaceAll("").trim();
             return matcher.find()
         }
     }
 }