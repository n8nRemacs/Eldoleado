package androidx.camera.view;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Rational;
import android.util.Size;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.annotation.RestrictTo;
import androidx.camera.core.C20140q0;
import androidx.camera.core.K0;
import androidx.camera.core.M;
import androidx.camera.core.P0;
import androidx.camera.core.Q0;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.w0;
import androidx.camera.core.s0;
import androidx.camera.core.t0;
import androidx.camera.core.v0;
import androidx.camera.view.y;
import androidx.core.view.C22823h0;
import androidx.view.AbstractC22991Y;
import androidx.view.C23038g0;
import j.InterfaceC42148d;
import j.K;
import j.N;
import j.P;
import j.S;
import j.X;
import j.j0;
import j.k0;
import java.util.concurrent.atomic.AtomicReference;

@X
/* loaded from: classes.dex */
public final class PreviewView extends FrameLayout {

    /* renamed from: p, reason: collision with root package name */
    public static final /* synthetic */ int f25379p = 0;

    /* renamed from: b, reason: collision with root package name */
    @N
    public ImplementationMode f25380b;

    /* renamed from: c, reason: collision with root package name */
    @k0
    @P
    public u f25381c;

    /* renamed from: d, reason: collision with root package name */
    @N
    public final r f25382d;

    /* renamed from: e, reason: collision with root package name */
    public boolean f25383e;

    /* renamed from: f, reason: collision with root package name */
    @N
    public final C23038g0<StreamState> f25384f;

    /* renamed from: g, reason: collision with root package name */
    @P
    public final AtomicReference<q> f25385g;

    /* renamed from: h, reason: collision with root package name */
    public AbstractC20193e f25386h;

    /* renamed from: i, reason: collision with root package name */
    @N
    public final v f25387i;

    /* renamed from: j, reason: collision with root package name */
    @N
    public final ScaleGestureDetector f25388j;

    /* renamed from: k, reason: collision with root package name */
    @P
    public androidx.camera.core.impl.B f25389k;

    /* renamed from: l, reason: collision with root package name */
    @P
    public MotionEvent f25390l;

    /* renamed from: m, reason: collision with root package name */
    @N
    public final c f25391m;

    /* renamed from: n, reason: collision with root package name */
    public final s f25392n;

    /* renamed from: o, reason: collision with root package name */
    public final v0.c f25393o;

    @X
    public enum ImplementationMode {
        PERFORMANCE(0),
        COMPATIBLE(1);


        /* renamed from: b, reason: collision with root package name */
        public final int f25397b;

        ImplementationMode(int i12) {
            this.f25397b = i12;
        }
    }

    @X
    public enum ScaleType {
        /* JADX INFO: Fake field, exist only in values array */
        FILL_START(0),
        FILL_CENTER(1),
        /* JADX INFO: Fake field, exist only in values array */
        FILL_END(2),
        FIT_START(3),
        FIT_CENTER(4),
        FIT_END(5);


        /* renamed from: b, reason: collision with root package name */
        public final int f25403b;

        ScaleType(int i12) {
            this.f25403b = i12;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class StreamState {

        /* renamed from: b, reason: collision with root package name */
        public static final StreamState f25404b;

        /* renamed from: c, reason: collision with root package name */
        public static final StreamState f25405c;

        /* renamed from: d, reason: collision with root package name */
        public static final /* synthetic */ StreamState[] f25406d;

        static {
            StreamState streamState = new StreamState("IDLE", 0);
            f25404b = streamState;
            StreamState streamState2 = new StreamState("STREAMING", 1);
            f25405c = streamState2;
            f25406d = new StreamState[]{streamState, streamState2};
        }

        public StreamState() {
            throw null;
        }

        public static StreamState valueOf(String str) {
            return (StreamState) Enum.valueOf(StreamState.class, str);
        }

        public static StreamState[] values() {
            return (StreamState[]) f25406d.clone();
        }
    }

    public class a implements v0.c {
        public a() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.camera.core.v0.c
        @InterfaceC42148d
        public final void a(@N K0 k02) {
            E e12;
            boolean zB2 = androidx.camera.core.impl.utils.t.b();
            PreviewView previewView = PreviewView.this;
            if (!zB2) {
                androidx.core.content.d.getMainExecutor(previewView.getContext()).execute(new t(0, this, k02));
                return;
            }
            C20140q0.d(3, "PreviewView");
            CameraInternal cameraInternal = k02.f23593e;
            previewView.f25389k = cameraInternal.c();
            k02.b(androidx.core.content.d.getMainExecutor(previewView.getContext()), new n(this, cameraInternal, k02));
            u uVar = previewView.f25381c;
            ImplementationMode implementationMode = previewView.f25380b;
            if (!(uVar instanceof E) || PreviewView.c(k02, implementationMode)) {
                boolean zC2 = PreviewView.c(k02, previewView.f25380b);
                r rVar = previewView.f25382d;
                if (zC2) {
                    I i12 = new I(previewView, rVar);
                    i12.f25375i = false;
                    i12.f25377k = new AtomicReference<>();
                    e12 = i12;
                } else {
                    e12 = new E(previewView, rVar);
                }
                previewView.f25381c = e12;
            }
            q qVar = new q(cameraInternal.c(), previewView.f25384f, previewView.f25381c);
            previewView.f25385g.set(qVar);
            cameraInternal.f().d(androidx.core.content.d.getMainExecutor(previewView.getContext()), qVar);
            previewView.f25381c.e(k02, new n(this, qVar, cameraInternal));
        }
    }

    public static /* synthetic */ class b {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f25408a;

        /* renamed from: b, reason: collision with root package name */
        public static final /* synthetic */ int[] f25409b;

        static {
            int[] iArr = new int[ImplementationMode.values().length];
            f25409b = iArr;
            try {
                iArr[1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f25409b[0] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[ScaleType.values().length];
            f25408a = iArr2;
            try {
                iArr2[2] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f25408a[1] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f25408a[0] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f25408a[5] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f25408a[4] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f25408a[3] = 6;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    @RestrictTo
    public interface d {
    }

    public class e extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        public e() {
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public final boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            AbstractC20193e abstractC20193e = PreviewView.this.f25386h;
            if (abstractC20193e == null) {
                return true;
            }
            float scaleFactor = scaleGestureDetector.getScaleFactor();
            if (!abstractC20193e.d()) {
                C20140q0.d(5, "CameraController");
                return true;
            }
            if (!abstractC20193e.f25427m) {
                C20140q0.d(3, "CameraController");
                return true;
            }
            C20140q0.d(3, "CameraController");
            androidx.camera.core.impl.utils.t.a();
            Q0 value = abstractC20193e.f25429o.getValue();
            if (value == null) {
                return true;
            }
            abstractC20193e.f(Math.min(Math.max(value.b() * (scaleFactor > 1.0f ? androidx.appcompat.app.r.c(scaleFactor, 1.0f, 2.0f, 1.0f) : 1.0f - ((1.0f - scaleFactor) * 2.0f)), value.d()), value.c()));
            return true;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r0v5, types: [androidx.camera.view.s] */
    @j0
    public PreviewView(@N Context context, @P AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        this.f25380b = ImplementationMode.PERFORMANCE;
        r rVar = new r();
        this.f25382d = rVar;
        this.f25383e = true;
        this.f25384f = new C23038g0<>(StreamState.f25404b);
        this.f25385g = new AtomicReference<>();
        this.f25387i = new v(rVar);
        this.f25391m = new c();
        this.f25392n = new View.OnLayoutChangeListener() { // from class: androidx.camera.view.s
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19) {
                int i22 = PreviewView.f25379p;
                PreviewView previewView = this.f25469b;
                if (i14 - i12 == i18 - i16 && i15 - i13 == i19 - i17) {
                    return;
                }
                previewView.b();
                previewView.a(true);
            }
        };
        this.f25393o = new a();
        androidx.camera.core.impl.utils.t.a();
        Resources.Theme theme = context.getTheme();
        int[] iArr = y.c.f25481a;
        TypedArray typedArrayObtainStyledAttributes = theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
        C22823h0.B(this, context, iArr, attributeSet, typedArrayObtainStyledAttributes, 0);
        try {
            int integer = typedArrayObtainStyledAttributes.getInteger(1, rVar.f25467h.f25403b);
            for (ScaleType scaleType : ScaleType.values()) {
                if (scaleType.f25403b == integer) {
                    setScaleType(scaleType);
                    int integer2 = typedArrayObtainStyledAttributes.getInteger(0, 0);
                    for (ImplementationMode implementationMode : ImplementationMode.values()) {
                        if (implementationMode.f25397b == integer2) {
                            setImplementationMode(implementationMode);
                            typedArrayObtainStyledAttributes.recycle();
                            this.f25388j = new ScaleGestureDetector(context, new e());
                            if (getBackground() == null) {
                                setBackgroundColor(androidx.core.content.d.getColor(getContext(), R.color.black));
                                return;
                            }
                            return;
                        }
                    }
                    throw new IllegalArgumentException("Unknown implementation mode id " + integer2);
                }
            }
            throw new IllegalArgumentException("Unknown scale type id " + integer);
        } catch (Throwable th2) {
            typedArrayObtainStyledAttributes.recycle();
            throw th2;
        }
    }

    public static boolean c(@N K0 k02, @N ImplementationMode implementationMode) {
        boolean zEquals = k02.f23593e.c().j().equals("androidx.camera.camera2.legacy");
        w0 w0Var = androidx.camera.view.internal.compat.quirk.a.f25440a;
        boolean z12 = (w0Var.b(androidx.camera.view.internal.compat.quirk.d.class) == null && w0Var.b(androidx.camera.view.internal.compat.quirk.c.class) == null) ? false : true;
        if (zEquals || z12) {
            return true;
        }
        int iOrdinal = implementationMode.ordinal();
        if (iOrdinal == 0) {
            return false;
        }
        if (iOrdinal == 1) {
            return true;
        }
        throw new IllegalArgumentException("Invalid implementation mode: " + implementationMode);
    }

    @P
    private DisplayManager getDisplayManager() {
        Context context = getContext();
        if (context == null) {
            return null;
        }
        return (DisplayManager) context.getApplicationContext().getSystemService("display");
    }

    private int getViewPortScaleType() {
        int iOrdinal = getScaleType().ordinal();
        if (iOrdinal == 0) {
            return 0;
        }
        int i12 = 1;
        if (iOrdinal != 1) {
            i12 = 2;
            if (iOrdinal != 2) {
                i12 = 3;
                if (iOrdinal != 3 && iOrdinal != 4 && iOrdinal != 5) {
                    throw new IllegalStateException("Unexpected scale type: " + getScaleType());
                }
            }
        }
        return i12;
    }

    @K
    public final void a(boolean z12) {
        androidx.camera.core.impl.utils.t.a();
        P0 viewPort = getViewPort();
        if (this.f25386h == null || viewPort == null || !isAttachedToWindow()) {
            return;
        }
        try {
            this.f25386h.a(getSurfaceProvider(), viewPort);
        } catch (IllegalStateException e12) {
            if (!z12) {
                throw e12;
            }
            e12.toString();
            C20140q0.c("PreviewView");
        }
    }

    @K
    @S
    public final void b() {
        Display display;
        androidx.camera.core.impl.B b12;
        androidx.camera.core.impl.utils.t.a();
        if (this.f25381c != null) {
            if (this.f25383e && (display = getDisplay()) != null && (b12 = this.f25389k) != null) {
                int iM2 = b12.m(display.getRotation());
                int rotation = display.getRotation();
                r rVar = this.f25382d;
                if (rVar.f25466g) {
                    rVar.f25462c = iM2;
                    rVar.f25464e = rotation;
                }
            }
            this.f25381c.f();
        }
        v vVar = this.f25387i;
        Size size = new Size(getWidth(), getHeight());
        int layoutDirection = getLayoutDirection();
        vVar.getClass();
        androidx.camera.core.impl.utils.t.a();
        synchronized (vVar) {
            try {
                if (size.getWidth() == 0 || size.getHeight() == 0) {
                    vVar.f25479b = null;
                } else {
                    vVar.f25479b = vVar.f25478a.a(size, layoutDirection);
                }
            } finally {
            }
        }
        if (this.f25386h != null) {
            getSensorToViewTransform();
            androidx.camera.core.impl.utils.t.a();
        }
    }

    @j0
    @P
    public Bitmap getBitmap() {
        Bitmap bitmapB;
        androidx.camera.core.impl.utils.t.a();
        u uVar = this.f25381c;
        if (uVar == null || (bitmapB = uVar.b()) == null) {
            return null;
        }
        FrameLayout frameLayout = uVar.f25474b;
        Size size = new Size(frameLayout.getWidth(), frameLayout.getHeight());
        int layoutDirection = frameLayout.getLayoutDirection();
        r rVar = uVar.f25475c;
        if (!rVar.f()) {
            return bitmapB;
        }
        Matrix matrixD = rVar.d();
        RectF rectFE = rVar.e(size, layoutDirection);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(size.getWidth(), size.getHeight(), bitmapB.getConfig());
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Matrix matrix = new Matrix();
        matrix.postConcat(matrixD);
        matrix.postScale(rectFE.width() / rVar.f25460a.getWidth(), rectFE.height() / rVar.f25460a.getHeight());
        matrix.postTranslate(rectFE.left, rectFE.top);
        canvas.drawBitmap(bitmapB, matrix, new Paint(7));
        return bitmapCreateBitmap;
    }

    @j0
    @P
    public AbstractC20193e getController() {
        androidx.camera.core.impl.utils.t.a();
        return this.f25386h;
    }

    @j0
    @N
    public ImplementationMode getImplementationMode() {
        androidx.camera.core.impl.utils.t.a();
        return this.f25380b;
    }

    @j0
    @N
    public t0 getMeteringPointFactory() {
        androidx.camera.core.impl.utils.t.a();
        return this.f25387i;
    }

    @J
    @P
    public H.d getOutputTransform() {
        Matrix matrixC;
        r rVar = this.f25382d;
        androidx.camera.core.impl.utils.t.a();
        try {
            matrixC = rVar.c(new Size(getWidth(), getHeight()), getLayoutDirection());
        } catch (IllegalStateException unused) {
            matrixC = null;
        }
        Rect rect = rVar.f25461b;
        if (matrixC == null || rect == null) {
            C20140q0.d(3, "PreviewView");
            return null;
        }
        RectF rectF = androidx.camera.core.impl.utils.u.f24303a;
        RectF rectF2 = new RectF(rect);
        Matrix matrix = new Matrix();
        matrix.setRectToRect(androidx.camera.core.impl.utils.u.f24303a, rectF2, Matrix.ScaleToFit.FILL);
        matrixC.preConcat(matrix);
        if (this.f25381c instanceof I) {
            matrixC.postConcat(getMatrix());
        } else if (!getMatrix().isIdentity()) {
            C20140q0.d(5, "PreviewView");
        }
        new Size(rect.width(), rect.height());
        return new H.d();
    }

    @N
    public AbstractC22991Y<StreamState> getPreviewStreamState() {
        return this.f25384f;
    }

    @j0
    @N
    public ScaleType getScaleType() {
        androidx.camera.core.impl.utils.t.a();
        return this.f25382d.f25467h;
    }

    @RestrictTo
    @P
    public Matrix getSensorToViewTransform() {
        androidx.camera.core.impl.utils.t.a();
        Size size = new Size(getWidth(), getHeight());
        int layoutDirection = getLayoutDirection();
        r rVar = this.f25382d;
        if (!rVar.f()) {
            return null;
        }
        Matrix matrix = new Matrix(rVar.f25463d);
        matrix.postConcat(rVar.c(size, layoutDirection));
        return matrix;
    }

    @j0
    @N
    public v0.c getSurfaceProvider() {
        androidx.camera.core.impl.utils.t.a();
        return this.f25393o;
    }

    @j0
    @P
    public P0 getViewPort() {
        androidx.camera.core.impl.utils.t.a();
        if (getDisplay() == null) {
            return null;
        }
        int rotation = getDisplay().getRotation();
        androidx.camera.core.impl.utils.t.a();
        if (getWidth() == 0 || getHeight() == 0) {
            return null;
        }
        Rational rational = new Rational(getWidth(), getHeight());
        P0.a aVar = new P0.a();
        aVar.f23651a = 1;
        aVar.f23651a = getViewPortScaleType();
        int layoutDirection = getLayoutDirection();
        int i12 = aVar.f23651a;
        P0 p02 = new P0();
        p02.f23647a = i12;
        p02.f23648b = rational;
        p02.f23649c = rotation;
        p02.f23650d = layoutDirection;
        return p02;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        DisplayManager displayManager = getDisplayManager();
        if (displayManager != null) {
            displayManager.registerDisplayListener(this.f25391m, new Handler(Looper.getMainLooper()));
        }
        addOnLayoutChangeListener(this.f25392n);
        u uVar = this.f25381c;
        if (uVar != null) {
            uVar.c();
        }
        a(true);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeOnLayoutChangeListener(this.f25392n);
        u uVar = this.f25381c;
        if (uVar != null) {
            uVar.d();
        }
        AbstractC20193e abstractC20193e = this.f25386h;
        if (abstractC20193e != null) {
            abstractC20193e.b();
        }
        DisplayManager displayManager = getDisplayManager();
        if (displayManager == null) {
            return;
        }
        displayManager.unregisterDisplayListener(this.f25391m);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(@N MotionEvent motionEvent) {
        if (this.f25386h == null) {
            return super.onTouchEvent(motionEvent);
        }
        boolean z12 = motionEvent.getPointerCount() == 1;
        boolean z13 = motionEvent.getAction() == 1;
        boolean z14 = motionEvent.getEventTime() - motionEvent.getDownTime() < ((long) ViewConfiguration.getLongPressTimeout());
        if (!z12 || !z13 || !z14) {
            return this.f25388j.onTouchEvent(motionEvent) || super.onTouchEvent(motionEvent);
        }
        this.f25390l = motionEvent;
        performClick();
        return true;
    }

    @Override // android.view.View
    public final boolean performClick() {
        if (this.f25386h != null) {
            MotionEvent motionEvent = this.f25390l;
            float x12 = motionEvent != null ? motionEvent.getX() : getWidth() / 2.0f;
            MotionEvent motionEvent2 = this.f25390l;
            float y12 = motionEvent2 != null ? motionEvent2.getY() : getHeight() / 2.0f;
            AbstractC20193e abstractC20193e = this.f25386h;
            if (!abstractC20193e.d()) {
                C20140q0.d(5, "CameraController");
            } else if (abstractC20193e.f25428n) {
                C20140q0.d(3, "CameraController");
                abstractC20193e.f25431q.postValue(1);
                v vVar = this.f25387i;
                s0 s0VarB = vVar.b(x12, y12, 0.16666667f);
                s0 s0VarB2 = vVar.b(x12, y12, 0.25f);
                M.a aVar = new M.a(s0VarB, 1);
                aVar.a(s0VarB2, 2);
                androidx.camera.core.impl.utils.futures.f.a(abstractC20193e.f25421g.a().g(new M(aVar)), new C20192d(abstractC20193e), androidx.camera.core.impl.utils.executor.c.a());
            } else {
                C20140q0.d(3, "CameraController");
            }
        }
        this.f25390l = null;
        return super.performClick();
    }

    @j0
    public void setController(@P AbstractC20193e abstractC20193e) {
        androidx.camera.core.impl.utils.t.a();
        AbstractC20193e abstractC20193e2 = this.f25386h;
        if (abstractC20193e2 != null && abstractC20193e2 != abstractC20193e) {
            abstractC20193e2.b();
        }
        this.f25386h = abstractC20193e;
        a(false);
    }

    @j0
    public void setImplementationMode(@N ImplementationMode implementationMode) {
        androidx.camera.core.impl.utils.t.a();
        this.f25380b = implementationMode;
    }

    @j0
    public void setScaleType(@N ScaleType scaleType) {
        androidx.camera.core.impl.utils.t.a();
        this.f25382d.f25467h = scaleType;
        b();
        a(false);
    }

    public class c implements DisplayManager.DisplayListener {
        public c() {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i12) {
            PreviewView previewView = PreviewView.this;
            Display display = previewView.getDisplay();
            if (display == null || display.getDisplayId() != i12) {
                return;
            }
            previewView.b();
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i12) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i12) {
        }
    }
}
