package androidx.camera.extensions.internal.sessionprocessor;

import D.h;
import android.content.Context;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.util.Size;
import android.view.Surface;
import androidx.camera.camera2.impl.b;
import androidx.camera.core.impl.A0;
import androidx.camera.core.impl.C0;
import androidx.camera.core.impl.CameraCaptureFailure;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.t0;
import androidx.camera.core.impl.y0;
import androidx.camera.extensions.impl.advanced.ImageProcessorImpl;
import androidx.camera.extensions.impl.advanced.ImageReferenceImpl;
import androidx.camera.extensions.impl.advanced.OutputSurfaceImpl;
import androidx.camera.extensions.impl.advanced.RequestProcessorImpl;
import androidx.camera.extensions.impl.advanced.SessionProcessorImpl;
import j.N;
import j.P;
import j.S;
import j.X;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@X
/* loaded from: classes.dex */
public class AdvancedSessionProcessor extends v {

    public static class CallbackAdapter implements y0.a {
        private final RequestProcessorImpl.Callback mCallback;

        public CallbackAdapter(RequestProcessorImpl.Callback callback) {
            this.mCallback = callback;
        }

        private RequestProcessorImpl.Request getImplRequest(y0.b bVar) {
            androidx.core.util.z.b(bVar instanceof RequestAdapter);
            return ((RequestAdapter) bVar).getImplRequest();
        }

        @Override // androidx.camera.core.impl.y0.a
        public void onCaptureBufferLost(@N y0.b bVar, long j12, int i12) {
            this.mCallback.onCaptureBufferLost(getImplRequest(bVar), j12, i12);
        }

        @Override // androidx.camera.core.impl.y0.a
        public void onCaptureCompleted(@N y0.b bVar, @P androidx.camera.core.impl.r rVar) {
            CaptureResult captureResultB = androidx.camera.camera2.impl.a.b(rVar);
            androidx.core.util.z.a("CaptureResult in cameraCaptureResult is not a TotalCaptureResult", captureResultB instanceof TotalCaptureResult);
            this.mCallback.onCaptureCompleted(getImplRequest(bVar), (TotalCaptureResult) captureResultB);
        }

        @Override // androidx.camera.core.impl.y0.a
        public void onCaptureFailed(@N y0.b bVar, @P CameraCaptureFailure cameraCaptureFailure) {
            CaptureFailure captureFailureA = androidx.camera.camera2.impl.a.a(cameraCaptureFailure);
            androidx.core.util.z.a("CameraCaptureFailure does not contain CaptureFailure.", captureFailureA != null);
            this.mCallback.onCaptureFailed(getImplRequest(bVar), captureFailureA);
        }

        @Override // androidx.camera.core.impl.y0.a
        public void onCaptureProgressed(@N y0.b bVar, @N androidx.camera.core.impl.r rVar) {
            CaptureResult captureResultB = androidx.camera.camera2.impl.a.b(rVar);
            androidx.core.util.z.a("Cannot get CaptureResult from the cameraCaptureResult ", captureResultB != null);
            this.mCallback.onCaptureProgressed(getImplRequest(bVar), captureResultB);
        }

        @Override // androidx.camera.core.impl.y0.a
        public void onCaptureSequenceAborted(int i12) {
            this.mCallback.onCaptureSequenceAborted(i12);
        }

        @Override // androidx.camera.core.impl.y0.a
        public void onCaptureSequenceCompleted(int i12, long j12) {
            this.mCallback.onCaptureSequenceCompleted(i12, j12);
        }

        @Override // androidx.camera.core.impl.y0.a
        public void onCaptureStarted(@N y0.b bVar, long j12, long j13) {
            this.mCallback.onCaptureStarted(getImplRequest(bVar), j12, j13);
        }
    }

    public static class ImageProcessorAdapter implements p {
        private final ImageProcessorImpl mImpl;

        public ImageProcessorAdapter(ImageProcessorImpl imageProcessorImpl) {
            this.mImpl = imageProcessorImpl;
        }

        public void onNextImageAvailable(int i12, long j12, @N r rVar, @P String str) {
            this.mImpl.onNextImageAvailable(i12, j12, new ImageReferenceImplAdapter(rVar), str);
        }
    }

    public static class ImageReferenceImplAdapter implements ImageReferenceImpl {
        private final r mImageReference;

        public ImageReferenceImplAdapter(r rVar) {
            this.mImageReference = rVar;
        }

        public boolean decrement() {
            return this.mImageReference.b();
        }

        @P
        public Image get() {
            return this.mImageReference.get();
        }

        public boolean increment() {
            return this.mImageReference.a();
        }
    }

    public static class OutputSurfaceImplAdapter implements OutputSurfaceImpl {
        private final t0 mOutputSurface;

        public OutputSurfaceImplAdapter(t0 t0Var) {
            this.mOutputSurface = t0Var;
        }

        public int getImageFormat() {
            return this.mOutputSurface.b();
        }

        @N
        public Size getSize() {
            return this.mOutputSurface.c();
        }

        @N
        public Surface getSurface() {
            return this.mOutputSurface.d();
        }
    }

    public static class RequestAdapter implements y0.b {
        private final RequestProcessorImpl.Request mImplRequest;
        private final Config mParameters;
        private final List<Integer> mTargetOutputConfigIds;
        private final int mTemplateId;

        @S
        public RequestAdapter(@N RequestProcessorImpl.Request request) {
            this.mImplRequest = request;
            ArrayList arrayList = new ArrayList();
            Iterator it = request.getTargetOutputConfigIds().iterator();
            while (it.hasNext()) {
                arrayList.add((Integer) it.next());
            }
            this.mTargetOutputConfigIds = arrayList;
            b.a aVar = new b.a();
            for (CaptureRequest.Key key : request.getParameters().keySet()) {
                aVar.c(key, request.getParameters().get(key));
            }
            this.mParameters = aVar.a();
            this.mTemplateId = request.getTemplateId().intValue();
        }

        @P
        public RequestProcessorImpl.Request getImplRequest() {
            return this.mImplRequest;
        }

        @Override // androidx.camera.core.impl.y0.b
        @N
        public Config getParameters() {
            return this.mParameters;
        }

        @Override // androidx.camera.core.impl.y0.b
        @N
        public List<Integer> getTargetOutputConfigIds() {
            return this.mTargetOutputConfigIds;
        }

        @Override // androidx.camera.core.impl.y0.b
        public int getTemplateId() {
            return this.mTemplateId;
        }
    }

    public class RequestProcessorImplAdapter implements RequestProcessorImpl {
        private final y0 mRequestProcessor;

        public RequestProcessorImplAdapter(@N y0 y0Var) {
            this.mRequestProcessor = y0Var;
        }

        public void abortCaptures() {
            this.mRequestProcessor.a();
        }

        public void setImageProcessor(int i12, @N ImageProcessorImpl imageProcessorImpl) {
            AdvancedSessionProcessor advancedSessionProcessor = AdvancedSessionProcessor.this;
            new ImageProcessorAdapter(imageProcessorImpl);
            advancedSessionProcessor.getClass();
            throw null;
        }

        public int setRepeating(@N RequestProcessorImpl.Request request, @N RequestProcessorImpl.Callback callback) {
            return this.mRequestProcessor.c(new RequestAdapter(request), new CallbackAdapter(callback));
        }

        public void stopRepeating() {
            this.mRequestProcessor.b();
        }

        public int submit(@N RequestProcessorImpl.Request request, @N RequestProcessorImpl.Callback callback) {
            return this.mRequestProcessor.e(new RequestAdapter(request), new CallbackAdapter(callback));
        }

        public int submit(@N List<RequestProcessorImpl.Request> list, @N RequestProcessorImpl.Callback callback) {
            ArrayList arrayList = new ArrayList();
            Iterator<RequestProcessorImpl.Request> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new RequestAdapter(it.next()));
            }
            return this.mRequestProcessor.d(arrayList, new CallbackAdapter(callback));
        }
    }

    @N
    @S
    public static HashMap j(@N D.h hVar) {
        HashMap map = new HashMap();
        D.h hVarA = h.a.c(hVar).a();
        for (Config.a<?> aVar : hVarA.e()) {
            map.put((CaptureRequest.Key) aVar.d(), hVarA.f(aVar));
        }
        return map;
    }

    @Override // androidx.camera.core.impl.C0
    public final void a() {
        SessionProcessorImpl sessionProcessorImpl = null;
        sessionProcessorImpl.onCaptureSessionEnd();
        throw null;
    }

    @Override // androidx.camera.extensions.internal.sessionprocessor.v, androidx.camera.core.impl.C0
    @N
    @A0.a
    public final /* bridge */ /* synthetic */ Set c() {
        return null;
    }

    @Override // androidx.camera.core.impl.C0
    public final void d(@N D.h hVar, @N C0.a aVar) {
        HashMap mapJ = j(hVar);
        androidx.camera.extensions.internal.l lVar = androidx.camera.extensions.internal.l.f24636b;
        if (androidx.camera.extensions.internal.d.c(lVar) && androidx.camera.extensions.internal.e.b(lVar)) {
            SessionProcessorImplCaptureCallbackAdapter sessionProcessorImplCaptureCallbackAdapter = new SessionProcessorImplCaptureCallbackAdapter(aVar);
            SessionProcessorImpl sessionProcessorImpl = null;
            sessionProcessorImpl.startTrigger(mapJ, sessionProcessorImplCaptureCallbackAdapter);
            throw null;
        }
    }

    @Override // androidx.camera.core.impl.C0
    public final void e(@N y0 y0Var) {
        RequestProcessorImplAdapter requestProcessorImplAdapter = new RequestProcessorImplAdapter(y0Var);
        SessionProcessorImpl sessionProcessorImpl = null;
        sessionProcessorImpl.onCaptureSessionStart(requestProcessorImplAdapter);
        throw null;
    }

    @Override // androidx.camera.core.impl.C0
    public final void g(@N androidx.camera.camera2.impl.b bVar) {
        SessionProcessorImpl sessionProcessorImpl = null;
        sessionProcessorImpl.setParameters(j(bVar));
        throw null;
    }

    @Override // androidx.camera.extensions.internal.sessionprocessor.v
    public final void h() {
        SessionProcessorImpl sessionProcessorImpl = null;
        sessionProcessorImpl.deInitSession();
        throw null;
    }

    @Override // androidx.camera.extensions.internal.sessionprocessor.v
    @N
    public final m i(@N String str, @N LinkedHashMap linkedHashMap, @N t0 t0Var, @N t0 t0Var2, @P t0 t0Var3) {
        SessionProcessorImpl sessionProcessorImpl = null;
        sessionProcessorImpl.initSession(str, linkedHashMap, (Context) null, new OutputSurfaceImplAdapter(t0Var), new OutputSurfaceImplAdapter(t0Var2), t0Var3 == null ? null : new OutputSurfaceImplAdapter(t0Var3));
        throw null;
    }

    public static class SessionProcessorImplCaptureCallbackAdapter implements SessionProcessorImpl.CaptureCallback {
        private final C0.a mCaptureCallback;

        public SessionProcessorImplCaptureCallbackAdapter(@N C0.a aVar) {
            this.mCaptureCallback = aVar;
        }

        public void onCaptureCompleted(long j12, int i12, Map<CaptureResult.Key, Object> map) {
            this.mCaptureCallback.getClass();
        }

        public void onCaptureFailed(int i12) {
            this.mCaptureCallback.b();
        }

        public void onCaptureProcessStarted(int i12) {
            this.mCaptureCallback.getClass();
        }

        public void onCaptureSequenceAborted(int i12) {
            this.mCaptureCallback.getClass();
        }

        public void onCaptureSequenceCompleted(int i12) {
            this.mCaptureCallback.a();
        }

        public void onCaptureStarted(int i12, long j12) {
            this.mCaptureCallback.getClass();
        }

        public void onCaptureProcessProgressed(int i12) {
        }
    }
}
