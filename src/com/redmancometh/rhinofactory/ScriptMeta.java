package com.redmancometh.rhinofactory;

import jdk.nashorn.internal.runtime.ScriptFunction;

public class ScriptMeta
{
    private ScriptFunction function;
    private boolean hasReturn;

    public ScriptMeta(ScriptFunction function, boolean hasReturn)
    {
        this.function = function;
        this.hasReturn = hasReturn;
    }

    public boolean hasReturn()
    {
        return hasReturn;
    }

    public void setHasReturn(boolean hasReturn)
    {
        this.hasReturn = hasReturn;
    }

    public ScriptFunction getFunction()
    {
        return function;
    }

    public void setFunction(ScriptFunction function)
    {
        this.function = function;
    }
}
