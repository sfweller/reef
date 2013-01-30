package com.microsoft.tang.implementation;

import com.microsoft.tang.ClassNode;
import com.microsoft.tang.ConstructorArg;
import com.microsoft.tang.ConstructorDef;
import com.microsoft.tang.exceptions.BindException;

public class ConstructorDefImpl<T> implements ConstructorDef<T> {
  private final ConstructorArg[] args;
  private final String className;

  @Override
  public ConstructorArg[] getArgs() {
    return args;
  }

  @Override
  public String getClassName() {
    return className;
  }

  private String join(String sep, Object[] vals) {
    if (vals.length != 0) {
      StringBuilder sb = new StringBuilder(vals[0].toString());
      for (int i = 1; i < vals.length; i++) {
        sb.append(sep + vals);
      }
      return sb.toString();
    } else {
      return "";
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(className);
    sb.append("(");
    sb.append(join(",", args));
    sb.append(")");
    return sb.toString();
  }

  public ConstructorDefImpl(String className, ConstructorArg[] args, boolean injectable)
      throws BindException {
    this.className = className;
    this.args = args;
    if (injectable) {
      for (int i = 0; i < this.getArgs().length; i++) {
        for (int j = i + 1; j < this.getArgs().length; j++) {
          if (this.getArgs()[i].equals(this.getArgs()[j])) {
            throw new BindException(
                "Repeated constructor parameter detected.  "
                    + "Cannot inject constructor" + toString());
          }
        }
      }
    }
  }

  @Override
  public boolean takesParameters(ClassNode<?>[] paramTypes) {
    if (paramTypes.length != args.length) {
      return false;
    }
    for (int i = 0; i < paramTypes.length; i++) {
      if (!args[i].getType().equals(paramTypes[i].getFullName())) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check to see if two boundConstructors take indistinguishable arguments. If
   * so (and they are in the same class), then this would lead to ambiguous
   * injection targets, and we want to fail fast.
   * 
   * TODO could be faster. Currently O(n^2) in number of parameters.
   * 
   * @param def
   * @return
   */
  private boolean equalsIgnoreOrder(ConstructorDef<?> def) {
    if (getArgs().length != def.getArgs().length) {
      return false;
    }
    for (int i = 0; i < getArgs().length; i++) {
      boolean found = false;
      for (int j = 0; j < getArgs().length; j++) {
        if (getArgs()[i].getName().equals(getArgs()[j].getName())) {
          found = true;
        }
      }
      if (!found) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean equals(Object o) {
    return equalsIgnoreOrder((ConstructorDef<?>) o);
  }

  @Override
  public boolean isMoreSpecificThan(ConstructorDef<?> def) {
    for (int i = 0; i < getArgs().length; i++) {
      boolean found = false;
      for (int j = 0; j < def.getArgs().length; j++) {
        if (getArgs()[i].equals(def.getArgs()[j])) {
          found = true;
        }
      }
      if (found == false)
        return false;
    }
    return getArgs().length > def.getArgs().length;
  }

  @Override
  public int compareTo(ConstructorDef<?> o) {
    return toString().compareTo(o.toString());
  }
}