package com.redmancometh.rhinofactory;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;

public class GeneratorUtil
{
    public static CtMethod generateGetter(CtClass declaringClass, String fieldName, Class fieldClass) throws CannotCompileException
    {
        String getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        StringBuffer sb = new StringBuffer();
        sb.append("public ").append(fieldClass.getName()).append(" ").append(getterName).append("(){").append("return this.").append(fieldName).append(";").append("}");
        return CtMethod.make(sb.toString(), declaringClass);
    }

    public static CtMethod generateSetter(CtClass declaringClass, String fieldName, Class fieldClass) throws CannotCompileException
    {
        String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        StringBuffer sb = new StringBuffer();
        sb.append("public void ").append(setterName).append("(").append(fieldClass.getName()).append(" ").append(fieldName).append(")").append("{").append("this.").append(fieldName).append("=").append(fieldName).append(";").append("}");
        return CtMethod.make(sb.toString(), declaringClass);
    }

}