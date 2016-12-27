package com.redmancometh.rhinofactory;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import jdk.nashorn.internal.runtime.ScriptFunction;

public class UnbornClass
{
    private String name;
    private CtClass classFetus;
    private Map<String, Object> fieldMap = new HashMap();
    private Map<String, ScriptMeta> methodMap = new HashMap();
    private List<Class> interfaceList = new ArrayList();
    public boolean extendsClass = false;
    private Class toExtend;
    private static ClassPool classPool = ClassPool.getDefault();
    private static CtClass objCT;
    private static CtClass methodHandle;
    {
        try
        {
            objCT = classPool.get("java.lang.Object");
            methodHandle = classPool.get("java.lang.invoke.MethodHandle");
        }
        catch (NotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public UnbornClass(String name)
    {
        this.setName(name);
    }

    /**
     * 
     * @param name
     * @param interfaces
     */
    public UnbornClass(String name, Class... interfaces)
    {
        this.setInterfaceList(Arrays.asList(interfaces));
        this.setName(name);
    }

    MethodHandle handle;

    public Class bake()
    {
        CtClass newClass = classPool.makeClass(name);
        try
        {
            for (Entry<String, Object> entry : fieldMap.entrySet())
            {
                CtField field = new CtField(objCT, entry.getKey(), newClass);
                field.setModifiers(Modifier.PUBLIC);
                newClass.addField(field);
                newClass.addMethod(GeneratorUtil.generateGetter(newClass, entry.getKey(), Object.class));
                newClass.addMethod(GeneratorUtil.generateSetter(newClass, entry.getKey(), Object.class));
            }
            for (Entry<String, ScriptMeta> entry : methodMap.entrySet())
            {
                ScriptMeta meta = entry.getValue();
                CtField field = new CtField(methodHandle, meta.getFunction().getName(), newClass);
                field.setModifiers(Modifier.PUBLIC);
                newClass.addField(field);
                if (!meta.hasReturn())
                {
                    newClass.addMethod(CtMethod.make("public void " + meta.getFunction().getName() + "(Object[] args) \n{" + " \ntry{" + meta.getFunction().getName() + ".invoke(args);}catch(Throwable e){e.printStackTrace();} \n " + "}", newClass));
                }
                else
                {
                    newClass.addMethod(CtMethod.make("public Object " + meta.getFunction().getName() + "(Object[] args) \n{ \nreturn " + meta.getFunction().getName() + ".invoke(args); \n }", newClass));
                }
            }
            for (Class inter : interfaceList)
            {
                newClass.addInterface(classPool.get(inter.getCanonicalName()));
            }
            if (extendsClass)
            {
                newClass.subtypeOf(classPool.get(toExtend.getCanonicalName()));
            }
            return newClass.toClass();
        }
        catch (CannotCompileException | NotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public UnbornClass(String name, Class extension, Class... interfaces)
    {
        this.extendsClass = true;
        this.setToExtend(extension);
        this.setInterfaceList(Arrays.asList(interfaces));
        this.setName(name);
    }

    public void addInterface(Class interfaceObject)
    {
        interfaceList.add(interfaceObject);
    }

    public void addField(String name, Object initValue)
    {
        this.fieldMap.put(name, initValue);
    }

    public void addMethod(String name, ScriptFunction inv, boolean hasReturn)
    {
        this.methodMap.put(name, new ScriptMeta(inv, !hasReturn));
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Class> getInterfaceList()
    {
        return interfaceList;
    }

    public void setInterfaceList(List<Class> interfaceList)
    {
        this.interfaceList = interfaceList;
    }

    public Class getToExtend()
    {
        return toExtend;
    }

    public void setIsSubClass(boolean subClass)
    {
        this.extendsClass = subClass;
    }

    public void setToExtend(Class toExtend)
    {
        if (toExtend != null)
        {
            this.extendsClass = true;
        }
        this.toExtend = toExtend;
    }

    public CtClass getClassFetus()
    {
        return classFetus;
    }

    public void setClassFetus(CtClass classFetus)
    {
        this.classFetus = classFetus;
    }

}
