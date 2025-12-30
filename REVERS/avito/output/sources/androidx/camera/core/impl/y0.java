package androidx.camera.core.impl;

import java.util.List;

/* compiled from: RequestProcessor.java */
@j.X
/* loaded from: classes.dex */
public interface y0 {

    /* compiled from: RequestProcessor.java */
    public interface b {
        @j.N
        Config getParameters();

        @j.N
        List<Integer> getTargetOutputConfigIds();

        int getTemplateId();
    }

    void a();

    void b();

    int c(@j.N b bVar, @j.N a aVar);

    int d(@j.N List<b> list, @j.N a aVar);

    int e(@j.N b bVar, @j.N a aVar);

    /* compiled from: RequestProcessor.java */
    public interface a {
        default void onCaptureSequenceAborted(int i12) {
        }

        default void onCaptureCompleted(@j.N b bVar, @j.N r rVar) {
        }

        default void onCaptureFailed(@j.N b bVar, @j.N CameraCaptureFailure cameraCaptureFailure) {
        }

        default void onCaptureProgressed(@j.N b bVar, @j.N r rVar) {
        }

        default void onCaptureSequenceCompleted(int i12, long j12) {
        }

        default void onCaptureBufferLost(@j.N b bVar, long j12, int i12) {
        }

        default void onCaptureStarted(@j.N b bVar, long j12, long j13) {
        }
    }
}
