package androidx.camera.extensions.internal.sessionprocessor;

import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.util.Pair;
import android.util.Size;
import android.view.Surface;
import androidx.camera.camera2.internal.C20010h;
import androidx.camera.core.C20130o0;
import androidx.camera.core.C20140q0;
import androidx.camera.core.InterfaceC20120j0;
import androidx.camera.core.impl.InterfaceC20092f0;
import androidx.camera.core.z0;
import androidx.camera.extensions.impl.CaptureProcessorImpl;
import androidx.camera.extensions.impl.ProcessResultImpl;
import androidx.camera.extensions.internal.sessionprocessor.o;
import androidx.camera.extensions.internal.sessionprocessor.z;
import j.B;
import j.F;
import j.N;
import j.X;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@X
/* loaded from: classes.dex */
class StillCaptureProcessor {
    private static final int MAX_IMAGES = 2;
    private static final String TAG = "StillCaptureProcessor";

    @N
    final CaptureProcessorImpl mCaptureProcessorImpl;

    @N
    final o mCaptureResultImageMatcher;

    @B
    @N
    HashMap<Integer, Pair<r, TotalCaptureResult>> mCaptureResults;

    @B
    boolean mIsClosed;
    final Object mLock;

    @B
    OnCaptureResultCallback mOnCaptureResultCallback;

    @N
    final InterfaceC20092f0 mProcessedYuvImageReader;

    @B
    TotalCaptureResult mSourceCaptureResult;

    @N
    z mYuvToJpegConverter;

    public interface OnCaptureResultCallback {
        void onCaptureResult(long j12, @N List<Pair<CaptureResult.Key, Object>> list);

        void onCompleted();

        void onError(@N Exception exc);
    }

    public StillCaptureProcessor(@N CaptureProcessorImpl captureProcessorImpl, @N Surface surface, @N Size size) {
        this.mCaptureResultImageMatcher = new o();
        this.mLock = new Object();
        this.mCaptureResults = new HashMap<>();
        this.mOnCaptureResultCallback = null;
        this.mSourceCaptureResult = null;
        this.mIsClosed = false;
        this.mCaptureProcessorImpl = captureProcessorImpl;
        InterfaceC20092f0 interfaceC20092f0A = C20130o0.a(size.getWidth(), size.getHeight(), 35, 2);
        this.mProcessedYuvImageReader = interfaceC20092f0A;
        this.mYuvToJpegConverter = new z(surface);
        interfaceC20092f0A.d(new InterfaceC20092f0.a() { // from class: androidx.camera.extensions.internal.sessionprocessor.x
            @Override // androidx.camera.core.impl.InterfaceC20092f0.a
            public final void a(InterfaceC20092f0 interfaceC20092f0) {
                this.f24652b.lambda$new$0(interfaceC20092f0);
            }
        }, androidx.camera.core.impl.utils.executor.c.c());
        captureProcessorImpl.onOutputSurface(interfaceC20092f0A.getSurface(), 35);
        captureProcessorImpl.onImageFormatUpdate(35);
        captureProcessorImpl.onResolutionUpdate(size);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$new$0(InterfaceC20092f0 interfaceC20092f0) {
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    C20140q0.d(3, TAG);
                    return;
                }
                InterfaceC20120j0 interfaceC20120j0A = interfaceC20092f0.a();
                TotalCaptureResult totalCaptureResult = this.mSourceCaptureResult;
                OnCaptureResultCallback onCaptureResultCallback = null;
                if (totalCaptureResult != null) {
                    z0 z0Var = new z0(interfaceC20120j0A, null, new androidx.camera.core.internal.c(new C20010h(totalCaptureResult)));
                    this.mSourceCaptureResult = null;
                    interfaceC20120j0A = z0Var;
                }
                if (interfaceC20120j0A != null) {
                    try {
                        this.mYuvToJpegConverter.a(interfaceC20120j0A);
                        e = null;
                    } catch (z.a e12) {
                        e = e12;
                    }
                    OnCaptureResultCallback onCaptureResultCallback2 = this.mOnCaptureResultCallback;
                    if (onCaptureResultCallback2 != null) {
                        this.mOnCaptureResultCallback = null;
                        onCaptureResultCallback = onCaptureResultCallback2;
                    }
                } else {
                    e = null;
                }
                if (onCaptureResultCallback != null) {
                    if (e != null) {
                        onCaptureResultCallback.onError(e);
                    } else {
                        onCaptureResultCallback.onCompleted();
                    }
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startCapture$1(List list, final OnCaptureResultCallback onCaptureResultCallback, r rVar, TotalCaptureResult totalCaptureResult, int i12) {
        synchronized (this.mLock) {
            try {
                if (this.mIsClosed) {
                    rVar.b();
                    C20140q0.a(TAG);
                    return;
                }
                C20140q0.a(TAG);
                this.mCaptureResults.put(Integer.valueOf(i12), new Pair<>(rVar, totalCaptureResult));
                Objects.toString(this.mCaptureResults.keySet());
                C20140q0.a(TAG);
                Exception exc = null;
                if (this.mCaptureResults.keySet().containsAll(list)) {
                    HashMap map = new HashMap();
                    for (Integer num : this.mCaptureResults.keySet()) {
                        Pair<r, TotalCaptureResult> pair = this.mCaptureResults.get(num);
                        map.put(num, new Pair(((r) pair.first).get(), (TotalCaptureResult) pair.second));
                    }
                    C20140q0.a(TAG);
                    try {
                        androidx.camera.extensions.internal.l lVar = androidx.camera.extensions.internal.l.f24636b;
                        if (androidx.camera.extensions.internal.e.b(lVar) && androidx.camera.extensions.internal.d.c(lVar)) {
                            this.mCaptureProcessorImpl.process(map, new ProcessResultImpl() { // from class: androidx.camera.extensions.internal.sessionprocessor.StillCaptureProcessor.1
                                public void onCaptureCompleted(long j12, @N List<Pair<CaptureResult.Key, Object>> list2) {
                                    onCaptureResultCallback.onCaptureResult(j12, list2);
                                }

                                public void onCaptureProcessProgressed(int i13) {
                                }
                            }, androidx.camera.core.impl.utils.executor.c.c());
                        } else {
                            this.mCaptureProcessorImpl.process(map);
                        }
                    } catch (Exception e12) {
                        this.mOnCaptureResultCallback = null;
                        exc = e12;
                    }
                    clearCaptureResults();
                }
                if (exc == null || onCaptureResultCallback == null) {
                    return;
                }
                onCaptureResultCallback.onError(exc);
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public void clearCaptureResults() {
        synchronized (this.mLock) {
            try {
                Iterator<Pair<r, TotalCaptureResult>> it = this.mCaptureResults.values().iterator();
                while (it.hasNext()) {
                    ((r) it.next().first).b();
                }
                this.mCaptureResults.clear();
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public void close() {
        C20140q0.d(3, TAG);
        synchronized (this.mLock) {
            this.mIsClosed = true;
            clearCaptureResults();
            this.mProcessedYuvImageReader.c();
            o oVar = this.mCaptureResultImageMatcher;
            synchronized (oVar.f24641a) {
                oVar.f24645e = null;
            }
            this.mCaptureResultImageMatcher.b();
            this.mProcessedYuvImageReader.close();
        }
    }

    public void notifyCaptureResult(@N TotalCaptureResult totalCaptureResult, int i12) {
        this.mCaptureResultImageMatcher.a(totalCaptureResult, i12);
        synchronized (this.mLock) {
            try {
                if (this.mSourceCaptureResult == null) {
                    this.mSourceCaptureResult = totalCaptureResult;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public void notifyImage(@N r rVar) {
        this.mCaptureResultImageMatcher.c(rVar);
    }

    public void setJpegQuality(@F int i12) {
        this.mYuvToJpegConverter.f24654b = i12;
    }

    public void setRotationDegrees(int i12) {
        this.mYuvToJpegConverter.f24655c = i12;
    }

    public void startCapture(@N final List<Integer> list, @N final OnCaptureResultCallback onCaptureResultCallback) {
        C20140q0.d(3, TAG);
        synchronized (this.mLock) {
            this.mOnCaptureResultCallback = onCaptureResultCallback;
            clearCaptureResults();
        }
        this.mCaptureResultImageMatcher.b();
        o oVar = this.mCaptureResultImageMatcher;
        o.a aVar = new o.a() { // from class: androidx.camera.extensions.internal.sessionprocessor.w
            @Override // androidx.camera.extensions.internal.sessionprocessor.o.a
            public final void a(r rVar, TotalCaptureResult totalCaptureResult, int i12) {
                this.f24649a.lambda$startCapture$1(list, onCaptureResultCallback, rVar, totalCaptureResult, i12);
            }
        };
        synchronized (oVar.f24641a) {
            oVar.f24645e = aVar;
        }
    }

    public StillCaptureProcessor(@N CaptureProcessorImpl captureProcessorImpl, @N Surface surface, @N Size size, @N z zVar) {
        this(captureProcessorImpl, surface, size);
        this.mYuvToJpegConverter = zVar;
    }
}
