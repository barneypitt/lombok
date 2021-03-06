/*
 * Copyright © 2009 Reinier Zwitserloot and Roel Spilker.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package lombok;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generates implementations for the <code>equals</code> and <code>hashCode</code> methods inherited by all objects.
 * <p>
 * If either method already exists, then <code>&#64;EqualsAndHashCode</code> will not generate that particular method.
 * If they all exist, <code>&#64;EqualsAndHashCode</code> generates no methods, and emits a warning instead to highlight
 * that its doing nothing at all. The parameter list and return type are not relevant when deciding to skip generation of
 * a method; any method named <code>hashCode</code> will make <code>&#64;EqualsAndHashCode</code> not generate that method,
 * for example.
 * <p>
 * By default, all fields that are non-static and non-transient are used in the equality check and hashCode generation.
 * You can exclude more fields by specifying them in the <code>exclude</code> parameter. You can also explicitly specify
 * the fields that are to be used by specifying them in the <code>of</code> parameter.
 * <p>
 * Normally, auto-generating <code>hashCode</code> and <code>equals</code> implementations in a subclass is a bad idea, as
 * the superclass also defines fields, for which equality checks/hashcodes won't be auto-generated. Therefore, a warning
 * is emitted when you try. Instead, you can set the <code>callSuper</code> parameter to <em>true</em> which will call
 * <code>super.equals</code> and <code>super.hashCode</code>. Doing this with <code>java.lang.Object</code> as superclass is
 * pointless, so, conversely, setting this flag when <em>NOT</em> extending something (other than Object) will also generate
 * a warning. Be aware that not all implementations of <code>equals</code> correctly handle being called from a subclass!
 * Fortunately, lombok-generated <code>equals</code> implementations do correctly handle it.
 * <p>
 * Array fields are handled by way of {@link java.util.Arrays#deepEquals(Object[], Object[])} where necessary, as well
 * as <code>deepHashCode</code>. The downside is that arrays with circular references (arrays that contain themselves,
 * possibly indirectly) results in calls to <code>hashCode</code> and <code>equals</code> throwing a
 * {@link java.lang.StackOverflowError}. However, the implementations for java's own {@link java.util.ArrayList} suffer
 * from the same flaw.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface EqualsAndHashCode {
	/**
	 * Any fields listed here will not be taken into account in the generated
	 * <code>equals</code> and <code>hashCode</code> implementations.
	 * Mutually exclusive with {@link #of()}.
	 */
	String[] exclude() default {};
	
	/**
	 * If present, explicitly lists the fields that are to be used for identity.
	 * Normally, all non-static, non-transient fields are used for identity.
	 * <p>
	 * Mutually exclusive with {@link #exclude()}.
	 */
	String[] of() default {};
	
	/**
	 * Call on the superclass's implementations of <code>equals</code> and <code>hashCode</code> before calculating
	 * for the fields in this class.
	 * <strong>default: false</strong>
	 */
	boolean callSuper() default false;
}
