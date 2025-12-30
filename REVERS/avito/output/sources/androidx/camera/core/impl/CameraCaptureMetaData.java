package androidx.camera.core.impl;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;

@j.X
/* loaded from: classes.dex */
public final class CameraCaptureMetaData {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class AeState {

        /* renamed from: b, reason: collision with root package name */
        public static final AeState f23877b;

        /* renamed from: c, reason: collision with root package name */
        public static final AeState f23878c;

        /* renamed from: d, reason: collision with root package name */
        public static final AeState f23879d;

        /* renamed from: e, reason: collision with root package name */
        public static final AeState f23880e;

        /* renamed from: f, reason: collision with root package name */
        public static final AeState f23881f;

        /* renamed from: g, reason: collision with root package name */
        public static final AeState f23882g;

        /* renamed from: h, reason: collision with root package name */
        public static final /* synthetic */ AeState[] f23883h;

        static {
            AeState aeState = new AeState(GrsBaseInfo.CountryCodeSource.UNKNOWN, 0);
            f23877b = aeState;
            AeState aeState2 = new AeState("INACTIVE", 1);
            f23878c = aeState2;
            AeState aeState3 = new AeState("SEARCHING", 2);
            f23879d = aeState3;
            AeState aeState4 = new AeState("FLASH_REQUIRED", 3);
            f23880e = aeState4;
            AeState aeState5 = new AeState("CONVERGED", 4);
            f23881f = aeState5;
            AeState aeState6 = new AeState("LOCKED", 5);
            f23882g = aeState6;
            f23883h = new AeState[]{aeState, aeState2, aeState3, aeState4, aeState5, aeState6};
        }

        public AeState() {
            throw null;
        }

        public static AeState valueOf(String str) {
            return (AeState) Enum.valueOf(AeState.class, str);
        }

        public static AeState[] values() {
            return (AeState[]) f23883h.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class AfMode {

        /* renamed from: b, reason: collision with root package name */
        public static final AfMode f23884b;

        /* renamed from: c, reason: collision with root package name */
        public static final AfMode f23885c;

        /* renamed from: d, reason: collision with root package name */
        public static final AfMode f23886d;

        /* renamed from: e, reason: collision with root package name */
        public static final AfMode f23887e;

        /* renamed from: f, reason: collision with root package name */
        public static final /* synthetic */ AfMode[] f23888f;

        static {
            AfMode afMode = new AfMode(GrsBaseInfo.CountryCodeSource.UNKNOWN, 0);
            f23884b = afMode;
            AfMode afMode2 = new AfMode("OFF", 1);
            f23885c = afMode2;
            AfMode afMode3 = new AfMode("ON_MANUAL_AUTO", 2);
            f23886d = afMode3;
            AfMode afMode4 = new AfMode("ON_CONTINUOUS_AUTO", 3);
            f23887e = afMode4;
            f23888f = new AfMode[]{afMode, afMode2, afMode3, afMode4};
        }

        public AfMode() {
            throw null;
        }

        public static AfMode valueOf(String str) {
            return (AfMode) Enum.valueOf(AfMode.class, str);
        }

        public static AfMode[] values() {
            return (AfMode[]) f23888f.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class AfState {

        /* renamed from: b, reason: collision with root package name */
        public static final AfState f23889b;

        /* renamed from: c, reason: collision with root package name */
        public static final AfState f23890c;

        /* renamed from: d, reason: collision with root package name */
        public static final AfState f23891d;

        /* renamed from: e, reason: collision with root package name */
        public static final AfState f23892e;

        /* renamed from: f, reason: collision with root package name */
        public static final AfState f23893f;

        /* renamed from: g, reason: collision with root package name */
        public static final AfState f23894g;

        /* renamed from: h, reason: collision with root package name */
        public static final AfState f23895h;

        /* renamed from: i, reason: collision with root package name */
        public static final /* synthetic */ AfState[] f23896i;

        static {
            AfState afState = new AfState(GrsBaseInfo.CountryCodeSource.UNKNOWN, 0);
            f23889b = afState;
            AfState afState2 = new AfState("INACTIVE", 1);
            f23890c = afState2;
            AfState afState3 = new AfState("SCANNING", 2);
            f23891d = afState3;
            AfState afState4 = new AfState("PASSIVE_FOCUSED", 3);
            f23892e = afState4;
            AfState afState5 = new AfState("PASSIVE_NOT_FOCUSED", 4);
            f23893f = afState5;
            AfState afState6 = new AfState("LOCKED_FOCUSED", 5);
            f23894g = afState6;
            AfState afState7 = new AfState("LOCKED_NOT_FOCUSED", 6);
            f23895h = afState7;
            f23896i = new AfState[]{afState, afState2, afState3, afState4, afState5, afState6, afState7};
        }

        public AfState() {
            throw null;
        }

        public static AfState valueOf(String str) {
            return (AfState) Enum.valueOf(AfState.class, str);
        }

        public static AfState[] values() {
            return (AfState[]) f23896i.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class AwbState {

        /* renamed from: b, reason: collision with root package name */
        public static final AwbState f23897b;

        /* renamed from: c, reason: collision with root package name */
        public static final AwbState f23898c;

        /* renamed from: d, reason: collision with root package name */
        public static final AwbState f23899d;

        /* renamed from: e, reason: collision with root package name */
        public static final AwbState f23900e;

        /* renamed from: f, reason: collision with root package name */
        public static final AwbState f23901f;

        /* renamed from: g, reason: collision with root package name */
        public static final /* synthetic */ AwbState[] f23902g;

        static {
            AwbState awbState = new AwbState(GrsBaseInfo.CountryCodeSource.UNKNOWN, 0);
            f23897b = awbState;
            AwbState awbState2 = new AwbState("INACTIVE", 1);
            f23898c = awbState2;
            AwbState awbState3 = new AwbState("METERING", 2);
            f23899d = awbState3;
            AwbState awbState4 = new AwbState("CONVERGED", 3);
            f23900e = awbState4;
            AwbState awbState5 = new AwbState("LOCKED", 4);
            f23901f = awbState5;
            f23902g = new AwbState[]{awbState, awbState2, awbState3, awbState4, awbState5};
        }

        public AwbState() {
            throw null;
        }

        public static AwbState valueOf(String str) {
            return (AwbState) Enum.valueOf(AwbState.class, str);
        }

        public static AwbState[] values() {
            return (AwbState[]) f23902g.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class FlashState {

        /* renamed from: b, reason: collision with root package name */
        public static final FlashState f23903b;

        /* renamed from: c, reason: collision with root package name */
        public static final FlashState f23904c;

        /* renamed from: d, reason: collision with root package name */
        public static final FlashState f23905d;

        /* renamed from: e, reason: collision with root package name */
        public static final FlashState f23906e;

        /* renamed from: f, reason: collision with root package name */
        public static final /* synthetic */ FlashState[] f23907f;

        static {
            FlashState flashState = new FlashState(GrsBaseInfo.CountryCodeSource.UNKNOWN, 0);
            f23903b = flashState;
            FlashState flashState2 = new FlashState("NONE", 1);
            f23904c = flashState2;
            FlashState flashState3 = new FlashState("READY", 2);
            f23905d = flashState3;
            FlashState flashState4 = new FlashState("FIRED", 3);
            f23906e = flashState4;
            f23907f = new FlashState[]{flashState, flashState2, flashState3, flashState4};
        }

        public FlashState() {
            throw null;
        }

        public static FlashState valueOf(String str) {
            return (FlashState) Enum.valueOf(FlashState.class, str);
        }

        public static FlashState[] values() {
            return (FlashState[]) f23907f.clone();
        }
    }
}
