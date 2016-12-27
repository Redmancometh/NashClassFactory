package com.redmancometh.rhinofactory;

import java.io.FileReader;
import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class RhinoFactory
{
    public static void main(String[] args)
    {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try (FileReader reader = new FileReader("genscripts\\master.js"))
        {
            engine.eval(reader);
        }
        catch (ScriptException | IOException e)
        {
            e.printStackTrace();
        }
    }
}
