package androidx.camera.core.processing;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.util.Size;
import android.view.Surface;
import androidx.camera.camera2.internal.G;
import androidx.camera.core.C20140q0;
import j.N;
import j.P;
import j.X;
import j.k0;
import j.l0;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import org.webrtc.EglBase;

/* compiled from: OpenGlRenderer.java */
@X
@l0
/* loaded from: classes.dex */
public final class p {

    /* renamed from: n, reason: collision with root package name */
    public static final String f24455n;

    /* renamed from: o, reason: collision with root package name */
    public static final String f24456o;

    /* renamed from: p, reason: collision with root package name */
    public static final String f24457p;

    /* renamed from: q, reason: collision with root package name */
    public static final String f24458q;

    /* renamed from: r, reason: collision with root package name */
    public static final FloatBuffer f24459r;

    /* renamed from: s, reason: collision with root package name */
    public static final FloatBuffer f24460s;

    /* renamed from: t, reason: collision with root package name */
    public static final C20134b f24461t;

    /* renamed from: c, reason: collision with root package name */
    @P
    public Thread f24464c;

    /* renamed from: f, reason: collision with root package name */
    @P
    public EGLConfig f24467f;

    /* renamed from: h, reason: collision with root package name */
    @P
    public Surface f24469h;

    /* renamed from: a, reason: collision with root package name */
    public final AtomicBoolean f24462a = new AtomicBoolean(false);

    /* renamed from: b, reason: collision with root package name */
    @k0
    public final HashMap f24463b = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    @N
    public EGLDisplay f24465d = EGL14.EGL_NO_DISPLAY;

    /* renamed from: e, reason: collision with root package name */
    @N
    public EGLContext f24466e = EGL14.EGL_NO_CONTEXT;

    /* renamed from: g, reason: collision with root package name */
    @N
    public EGLSurface f24468g = EGL14.EGL_NO_SURFACE;

    /* renamed from: i, reason: collision with root package name */
    public int f24470i = -1;

    /* renamed from: j, reason: collision with root package name */
    public int f24471j = -1;

    /* renamed from: k, reason: collision with root package name */
    public int f24472k = -1;

    /* renamed from: l, reason: collision with root package name */
    public int f24473l = -1;

    /* renamed from: m, reason: collision with root package name */
    public int f24474m = -1;

    /* compiled from: OpenGlRenderer.java */
    @VY0.c
    public static abstract class a {
        @N
        public abstract EGLSurface a();

        public abstract int b();

        public abstract int c();
    }

    static {
        Locale locale = Locale.US;
        f24455n = "uniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n";
        f24456o = "#version 300 es\nin vec4 aPosition;\nin vec4 aTextureCoord;\nuniform mat4 uTexMatrix;\nout vec2 vTextureCoord;\nvoid main() {\n  gl_Position = aPosition;\n  vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n";
        f24457p = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
        f24458q = "#version 300 es\n#extension GL_OES_EGL_image_external : require\n#extension GL_EXT_YUV_target : require\nprecision mediump float;\nuniform __samplerExternal2DY2YEXT sTexture;\nin vec2 vTextureCoord;\nout vec4 outColor;\n\nvec3 yuvToRgb(vec3 yuv) {\n  const vec3 yuvOffset = vec3(0.0625, 0.5, 0.5);\n  const mat3 yuvToRgbColorTransform = mat3(\n    1.1689f, 1.1689f, 1.1689f,\n    0.0000f, -0.1881f, 2.1502f,\n    1.6853f, -0.6530f, 0.0000f\n  );\n  return clamp(yuvToRgbColorTransform * (yuv - yuvOffset), 0.0, 1.0);\n}\n\nvoid main() {\n  vec3 srcYuv = texture(sTexture, vTextureCoord).xyz;\n  outColor = vec4(yuvToRgb(srcYuv), 1.0);\n}";
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(32);
        byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferAsFloatBuffer = byteBufferAllocateDirect.asFloatBuffer();
        floatBufferAsFloatBuffer.put(new float[]{-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f});
        floatBufferAsFloatBuffer.position(0);
        f24459r = floatBufferAsFloatBuffer;
        ByteBuffer byteBufferAllocateDirect2 = ByteBuffer.allocateDirect(32);
        byteBufferAllocateDirect2.order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferAsFloatBuffer2 = byteBufferAllocateDirect2.asFloatBuffer();
        floatBufferAsFloatBuffer2.put(new float[]{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f});
        floatBufferAsFloatBuffer2.position(0);
        f24460s = floatBufferAsFloatBuffer2;
        f24461t = new C20134b(EGL14.EGL_NO_SURFACE, 0, 0);
    }

    public static void a(@N String str) {
        int iEglGetError = EGL14.eglGetError();
        if (iEglGetError != 12288) {
            throw new IllegalStateException(androidx.appcompat.app.r.m(iEglGetError, androidx.appcompat.app.r.z(str, ": EGL error: 0x")));
        }
    }

    public static void b(@N String str) {
        int iGlGetError = GLES20.glGetError();
        if (iGlGetError != 0) {
            throw new IllegalStateException(androidx.appcompat.app.r.m(iGlGetError, androidx.appcompat.app.r.z(str, ": GL error 0x")));
        }
    }

    public static void e(int i12, @N String str) {
        if (i12 < 0) {
            throw new IllegalStateException(AK.c.k("Unable to locate '", str, "' in program"));
        }
    }

    @N
    public static EGLSurface j(@N EGLDisplay eGLDisplay, @N EGLConfig eGLConfig, @N Surface surface) {
        EGLSurface eGLSurfaceEglCreateWindowSurface = EGL14.eglCreateWindowSurface(eGLDisplay, eGLConfig, surface, new int[]{12344}, 0);
        a("eglCreateWindowSurface");
        if (eGLSurfaceEglCreateWindowSurface != null) {
            return eGLSurfaceEglCreateWindowSurface;
        }
        throw new IllegalStateException("surface was null");
    }

    public static int m(@N androidx.camera.core.F f12, @N s sVar) {
        if (sVar == s.f24475a) {
            return o(35632, f12.a() ? f24458q : f24457p);
        }
        try {
            sVar.getClass();
            throw new IllegalArgumentException("Invalid fragment shader");
        } catch (Throwable th2) {
            if (th2 instanceof IllegalArgumentException) {
                throw th2;
            }
            throw new IllegalArgumentException("Unable to compile fragment shader", th2);
        }
    }

    public static int o(int i12, @N String str) {
        int iGlCreateShader = GLES20.glCreateShader(i12);
        b("glCreateShader type=" + i12);
        GLES20.glShaderSource(iGlCreateShader, str);
        GLES20.glCompileShader(iGlCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(iGlCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return iGlCreateShader;
        }
        C20140q0.d(5, "OpenGlRenderer");
        GLES20.glDeleteShader(iGlCreateShader);
        StringBuilder sbJ = G.j(i12, "Could not compile shader type ", ":");
        sbJ.append(GLES20.glGetShaderInfoLog(iGlCreateShader));
        throw new IllegalStateException(sbJ.toString());
    }

    public final void c() {
        androidx.core.util.z.g("Method call must be called on the GL thread.", this.f24464c == Thread.currentThread());
    }

    public final void d(boolean z12) {
        androidx.core.util.z.g(z12 ? "OpenGlRenderer is not initialized" : "OpenGlRenderer is already initialized", z12 == this.f24462a.get());
    }

    public final void f(@N androidx.camera.core.F f12) {
        EGLDisplay eGLDisplayEglGetDisplay = EGL14.eglGetDisplay(0);
        this.f24465d = eGLDisplayEglGetDisplay;
        if (Objects.equals(eGLDisplayEglGetDisplay, EGL14.EGL_NO_DISPLAY)) {
            throw new IllegalStateException("Unable to get EGL14 display");
        }
        int[] iArr = new int[2];
        if (!EGL14.eglInitialize(this.f24465d, iArr, 0, iArr, 1)) {
            this.f24465d = EGL14.EGL_NO_DISPLAY;
            throw new IllegalStateException("Unable to initialize EGL14");
        }
        int i12 = f12.a() ? 10 : 8;
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (!EGL14.eglChooseConfig(this.f24465d, new int[]{12324, i12, 12323, i12, 12322, i12, 12321, f12.a() ? 2 : 8, 12325, 0, 12326, 0, 12352, f12.a() ? 64 : 4, EglBase.EGL_RECORDABLE_ANDROID, !f12.a() ? 1 : 0, 12339, 5, 12344}, 0, eGLConfigArr, 0, 1, new int[1], 0)) {
            throw new IllegalStateException("Unable to find a suitable EGLConfig");
        }
        EGLConfig eGLConfig = eGLConfigArr[0];
        EGLContext eGLContextEglCreateContext = EGL14.eglCreateContext(this.f24465d, eGLConfig, EGL14.EGL_NO_CONTEXT, new int[]{12440, f12.a() ? 3 : 2, 12344}, 0);
        a("eglCreateContext");
        this.f24467f = eGLConfig;
        this.f24466e = eGLContextEglCreateContext;
        EGL14.eglQueryContext(this.f24465d, eGLContextEglCreateContext, 12440, new int[1], 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void g(@j.N androidx.camera.core.F r9, @j.N androidx.camera.core.processing.s r10) throws java.lang.Throwable {
        /*
            r8 = this;
            java.lang.String r0 = "glAttachShader"
            java.lang.String r1 = "Could not link program: "
            r2 = -1
            boolean r3 = r9.a()     // Catch: java.lang.IllegalArgumentException -> Le java.lang.IllegalStateException -> L14
            if (r3 == 0) goto L16
            java.lang.String r3 = androidx.camera.core.processing.p.f24456o     // Catch: java.lang.IllegalArgumentException -> Le java.lang.IllegalStateException -> L14
            goto L18
        Le:
            r9 = move-exception
        Lf:
            r10 = r2
            r0 = r10
            r3 = r0
            goto L78
        L14:
            r9 = move-exception
            goto Lf
        L16:
            java.lang.String r3 = androidx.camera.core.processing.p.f24455n     // Catch: java.lang.IllegalArgumentException -> Le java.lang.IllegalStateException -> L14
        L18:
            r4 = 35633(0x8b31, float:4.9932E-41)
            int r3 = o(r4, r3)     // Catch: java.lang.IllegalArgumentException -> Le java.lang.IllegalStateException -> L14
            int r9 = m(r9, r10)     // Catch: java.lang.IllegalArgumentException -> L72 java.lang.IllegalStateException -> L76
            int r10 = android.opengl.GLES20.glCreateProgram()     // Catch: java.lang.IllegalArgumentException -> L6a java.lang.IllegalStateException -> L70
            java.lang.String r4 = "glCreateProgram"
            b(r4)     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            android.opengl.GLES20.glAttachShader(r10, r3)     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            b(r0)     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            android.opengl.GLES20.glAttachShader(r10, r9)     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            b(r0)     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            android.opengl.GLES20.glLinkProgram(r10)     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            r0 = 1
            int[] r4 = new int[r0]     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            r5 = 35714(0x8b82, float:5.0046E-41)
            r6 = 0
            android.opengl.GLES20.glGetProgramiv(r10, r5, r4, r6)     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            r4 = r4[r6]     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            if (r4 != r0) goto L54
            r8.f24471j = r10     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            return
        L4c:
            r0 = move-exception
        L4d:
            r7 = r10
            r10 = r9
            r9 = r0
            r0 = r7
            goto L78
        L52:
            r0 = move-exception
            goto L4d
        L54:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            r4.<init>(r1)     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            java.lang.String r1 = android.opengl.GLES20.glGetProgramInfoLog(r10)     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            r4.append(r1)     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            java.lang.String r1 = r4.toString()     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            r0.<init>(r1)     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
            throw r0     // Catch: java.lang.IllegalArgumentException -> L4c java.lang.IllegalStateException -> L52
        L6a:
            r10 = move-exception
        L6b:
            r0 = r2
            r7 = r10
            r10 = r9
            r9 = r7
            goto L78
        L70:
            r10 = move-exception
            goto L6b
        L72:
            r9 = move-exception
        L73:
            r10 = r2
            r0 = r10
            goto L78
        L76:
            r9 = move-exception
            goto L73
        L78:
            if (r3 == r2) goto L7d
            android.opengl.GLES20.glDeleteShader(r3)
        L7d:
            if (r10 == r2) goto L82
            android.opengl.GLES20.glDeleteShader(r10)
        L82:
            if (r0 == r2) goto L87
            android.opengl.GLES20.glDeleteProgram(r0)
        L87:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.processing.p.g(androidx.camera.core.F, androidx.camera.core.processing.s):void");
    }

    public final void h() {
        EGLDisplay eGLDisplay = this.f24465d;
        EGLConfig eGLConfig = this.f24467f;
        Objects.requireNonNull(eGLConfig);
        EGLSurface eGLSurfaceEglCreatePbufferSurface = EGL14.eglCreatePbufferSurface(eGLDisplay, eGLConfig, new int[]{12375, 1, 12374, 1, 12344}, 0);
        a("eglCreatePbufferSurface");
        if (eGLSurfaceEglCreatePbufferSurface == null) {
            throw new IllegalStateException("surface was null");
        }
        this.f24468g = eGLSurfaceEglCreatePbufferSurface;
    }

    public final void i() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        b("glGenTextures");
        int i12 = iArr[0];
        GLES20.glBindTexture(36197, i12);
        b("glBindTexture " + i12);
        GLES20.glTexParameterf(36197, 10241, 9728.0f);
        GLES20.glTexParameterf(36197, 10240, 9729.0f);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        b("glTexParameter");
        this.f24470i = i12;
    }

    @N
    public final String k(@N androidx.camera.core.F f12) {
        d(false);
        try {
            f(f12);
            h();
            p(this.f24468g);
            String strGlGetString = GLES20.glGetString(7939);
            return strGlGetString != null ? strGlGetString : "";
        } catch (IllegalStateException e12) {
            e12.getMessage();
            C20140q0.d(5, "OpenGlRenderer");
            return "";
        } finally {
            q();
        }
    }

    public final void l(@N androidx.camera.core.F f12, @N s sVar) {
        d(false);
        try {
            if (f12.a() && !k(f12).contains("GL_EXT_YUV_target")) {
                f12 = androidx.camera.core.F.f23562d;
            }
            f(f12);
            h();
            p(this.f24468g);
            g(f12, sVar);
            n();
            i();
            t();
            this.f24464c = Thread.currentThread();
            this.f24462a.set(true);
        } catch (IllegalArgumentException e12) {
            e = e12;
            q();
            throw e;
        } catch (IllegalStateException e13) {
            e = e13;
            q();
            throw e;
        }
    }

    public final void n() {
        int iGlGetAttribLocation = GLES20.glGetAttribLocation(this.f24471j, "aPosition");
        this.f24473l = iGlGetAttribLocation;
        e(iGlGetAttribLocation, "aPosition");
        int iGlGetAttribLocation2 = GLES20.glGetAttribLocation(this.f24471j, "aTextureCoord");
        this.f24474m = iGlGetAttribLocation2;
        e(iGlGetAttribLocation2, "aTextureCoord");
        int iGlGetUniformLocation = GLES20.glGetUniformLocation(this.f24471j, "uTexMatrix");
        this.f24472k = iGlGetUniformLocation;
        e(iGlGetUniformLocation, "uTexMatrix");
    }

    public final void p(@N EGLSurface eGLSurface) {
        this.f24465d.getClass();
        this.f24466e.getClass();
        if (!EGL14.eglMakeCurrent(this.f24465d, eGLSurface, eGLSurface, this.f24466e)) {
            throw new IllegalStateException("eglMakeCurrent failed");
        }
    }

    public final void q() {
        int i12 = this.f24471j;
        if (i12 != -1) {
            GLES20.glDeleteProgram(i12);
            this.f24471j = -1;
        }
        if (!Objects.equals(this.f24465d, EGL14.EGL_NO_DISPLAY)) {
            EGLDisplay eGLDisplay = this.f24465d;
            EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
            EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
            HashMap map = this.f24463b;
            for (a aVar : map.values()) {
                if (!Objects.equals(aVar.a(), EGL14.EGL_NO_SURFACE) && !EGL14.eglDestroySurface(this.f24465d, aVar.a())) {
                    try {
                        a("eglDestroySurface");
                    } catch (IllegalStateException e12) {
                        e12.toString();
                        C20140q0.c("OpenGlRenderer");
                    }
                }
            }
            map.clear();
            if (!Objects.equals(this.f24468g, EGL14.EGL_NO_SURFACE)) {
                EGL14.eglDestroySurface(this.f24465d, this.f24468g);
                this.f24468g = EGL14.EGL_NO_SURFACE;
            }
            if (!Objects.equals(this.f24466e, EGL14.EGL_NO_CONTEXT)) {
                EGL14.eglDestroyContext(this.f24465d, this.f24466e);
                this.f24466e = EGL14.EGL_NO_CONTEXT;
            }
            EGL14.eglReleaseThread();
            EGL14.eglTerminate(this.f24465d);
            this.f24465d = EGL14.EGL_NO_DISPLAY;
        }
        this.f24467f = null;
        this.f24471j = -1;
        this.f24472k = -1;
        this.f24473l = -1;
        this.f24474m = -1;
        this.f24470i = -1;
        this.f24469h = null;
        this.f24464c = null;
    }

    public final void r(@N Surface surface, boolean z12) {
        if (this.f24469h == surface) {
            this.f24469h = null;
            p(this.f24468g);
        }
        HashMap map = this.f24463b;
        C20134b c20134b = f24461t;
        a aVar = z12 ? (a) map.remove(surface) : (a) map.put(surface, c20134b);
        if (aVar == null || aVar == c20134b) {
            return;
        }
        try {
            EGL14.eglDestroySurface(this.f24465d, aVar.a());
        } catch (RuntimeException e12) {
            e12.getMessage();
            C20140q0.d(5, "OpenGlRenderer");
        }
    }

    public final void s(long j12, @N float[] fArr, @N Surface surface) {
        d(true);
        c();
        HashMap map = this.f24463b;
        androidx.core.util.z.g("The surface is not registered.", map.containsKey(surface));
        a c20134b = (a) map.get(surface);
        Objects.requireNonNull(c20134b);
        if (c20134b == f24461t) {
            try {
                EGLDisplay eGLDisplay = this.f24465d;
                EGLConfig eGLConfig = this.f24467f;
                Objects.requireNonNull(eGLConfig);
                EGLSurface eGLSurfaceJ = j(eGLDisplay, eGLConfig, surface);
                int[] iArr = new int[1];
                EGL14.eglQuerySurface(this.f24465d, eGLSurfaceJ, 12375, iArr, 0);
                int i12 = iArr[0];
                int[] iArr2 = new int[1];
                EGL14.eglQuerySurface(this.f24465d, eGLSurfaceJ, 12374, iArr2, 0);
                Size size = new Size(i12, iArr2[0]);
                c20134b = new C20134b(eGLSurfaceJ, size.getWidth(), size.getHeight());
            } catch (IllegalArgumentException | IllegalStateException e12) {
                e12.getMessage();
                C20140q0.d(5, "OpenGlRenderer");
                c20134b = null;
            }
            if (c20134b == null) {
                return;
            } else {
                map.put(surface, c20134b);
            }
        }
        if (surface != this.f24469h) {
            p(c20134b.a());
            this.f24469h = surface;
            GLES20.glViewport(0, 0, c20134b.c(), c20134b.b());
            GLES20.glScissor(0, 0, c20134b.c(), c20134b.b());
        }
        GLES20.glUniformMatrix4fv(this.f24472k, 1, false, fArr, 0);
        b("glUniformMatrix4fv");
        GLES20.glDrawArrays(5, 0, 4);
        b("glDrawArrays");
        EGLExt.eglPresentationTimeANDROID(this.f24465d, c20134b.a(), j12);
        if (EGL14.eglSwapBuffers(this.f24465d, c20134b.a())) {
            return;
        }
        Integer.toHexString(EGL14.eglGetError());
        C20140q0.d(5, "OpenGlRenderer");
        r(surface, false);
    }

    public final void t() {
        GLES20.glUseProgram(this.f24471j);
        b("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(36197, this.f24470i);
        GLES20.glEnableVertexAttribArray(this.f24473l);
        b("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.f24473l, 2, 5126, false, 0, (Buffer) f24459r);
        b("glVertexAttribPointer");
        GLES20.glEnableVertexAttribArray(this.f24474m);
        b("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.f24474m, 2, 5126, false, 0, (Buffer) f24460s);
        b("glVertexAttribPointer");
    }
}
