package androidx.appcompat.app;

import android.R;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.C19953i;
import androidx.appcompat.widget.C19961m;
import androidx.collection.o1;
import j.N;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: AppCompatViewInflater.java */
/* loaded from: classes.dex */
public class B {

    /* renamed from: b, reason: collision with root package name */
    public static final Class<?>[] f21473b = {Context.class, AttributeSet.class};

    /* renamed from: c, reason: collision with root package name */
    public static final int[] f21474c = {R.attr.onClick};

    /* renamed from: d, reason: collision with root package name */
    public static final int[] f21475d = {R.attr.accessibilityHeading};

    /* renamed from: e, reason: collision with root package name */
    public static final int[] f21476e = {R.attr.accessibilityPaneTitle};

    /* renamed from: f, reason: collision with root package name */
    public static final int[] f21477f = {R.attr.screenReaderFocusable};

    /* renamed from: g, reason: collision with root package name */
    public static final String[] f21478g = {"android.widget.", "android.view.", "android.webkit."};

    /* renamed from: h, reason: collision with root package name */
    public static final o1<String, Constructor<? extends View>> f21479h = new o1<>();

    /* renamed from: a, reason: collision with root package name */
    public final Object[] f21480a = new Object[2];

    /* compiled from: AppCompatViewInflater.java */
    public static class a implements View.OnClickListener {

        /* renamed from: b, reason: collision with root package name */
        public final View f21481b;

        /* renamed from: c, reason: collision with root package name */
        public final String f21482c;

        /* renamed from: d, reason: collision with root package name */
        public Method f21483d;

        /* renamed from: e, reason: collision with root package name */
        public Context f21484e;

        public a(@N View view, @N String str) {
            this.f21481b = view;
            this.f21482c = str;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(@N View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            String str;
            Method method;
            if (this.f21483d == null) {
                View view2 = this.f21481b;
                Context context = view2.getContext();
                while (true) {
                    String str2 = this.f21482c;
                    if (context == null) {
                        int id2 = view2.getId();
                        if (id2 == -1) {
                            str = "";
                        } else {
                            str = " with id '" + view2.getContext().getResources().getResourceEntryName(id2) + "'";
                        }
                        StringBuilder sbA = r.A("Could not find method ", str2, "(View) in a parent or ancestor Context for android:onClick attribute defined on view ");
                        sbA.append(view2.getClass());
                        sbA.append(str);
                        throw new IllegalStateException(sbA.toString());
                    }
                    try {
                        if (!context.isRestricted() && (method = context.getClass().getMethod(str2, View.class)) != null) {
                            this.f21483d = method;
                            this.f21484e = context;
                        }
                    } catch (NoSuchMethodException unused) {
                    }
                    context = context instanceof ContextWrapper ? ((ContextWrapper) context).getBaseContext() : null;
                }
            }
            try {
                this.f21483d.invoke(this.f21484e, view);
            } catch (IllegalAccessException e12) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", e12);
            } catch (InvocationTargetException e13) {
                throw new IllegalStateException("Could not execute method for android:onClick", e13);
            }
        }
    }

    @N
    public C19953i a(Context context, AttributeSet attributeSet) {
        return new C19953i(context, attributeSet);
    }

    @N
    public C19961m b(Context context, AttributeSet attributeSet) {
        return new C19961m(context, attributeSet);
    }

    @N
    public AppCompatCheckBox c(Context context, AttributeSet attributeSet) {
        return new AppCompatCheckBox(context, attributeSet);
    }

    @N
    public androidx.appcompat.widget.E d(Context context, AttributeSet attributeSet) {
        return new androidx.appcompat.widget.E(context, attributeSet, com.avito.android.R.attr.radioButtonStyle);
    }

    @N
    public AppCompatTextView e(Context context, AttributeSet attributeSet) {
        return new AppCompatTextView(context, attributeSet);
    }

    public final View f(Context context, String str, String str2) throws NoSuchMethodException, SecurityException {
        String strConcat;
        o1<String, Constructor<? extends View>> o1Var = f21479h;
        Constructor<? extends View> constructor = o1Var.get(str);
        if (constructor == null) {
            if (str2 != null) {
                try {
                    strConcat = str2.concat(str);
                } catch (Exception unused) {
                    return null;
                }
            } else {
                strConcat = str;
            }
            constructor = Class.forName(strConcat, false, context.getClassLoader()).asSubclass(View.class).getConstructor(f21473b);
            o1Var.put(str, constructor);
        }
        constructor.setAccessible(true);
        return constructor.newInstance(this.f21480a);
    }
}
