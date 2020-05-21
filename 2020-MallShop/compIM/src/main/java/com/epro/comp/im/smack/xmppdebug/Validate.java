package com.epro.comp.im.smack.xmppdebug; /**
 * Created by WangQX on 2018/4/16.
 * Description:此类用于
 */

/**
 * This is package-level helper class to validate dependencies while initialization is in progress
 */
final class Validate
{
    private Validate()
    { /* do not create instances */ }

    public static <T> T notNull(T instance)
    {
        return notNull(instance, null);
    }

    public static <T> T notNull(T instance, String message)
    {
        if (instance == null)
        {
            throw new NullPointerException(message);
        }
        else
        {
            return instance;
        }
    }
}
