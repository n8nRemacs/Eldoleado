package androidx.annotation;

import P41.c;
import P41.d;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;

/* JADX WARN: Method from annotation default annotation not found: level */
/* compiled from: RequiresOptIn.kt */
@Target({ElementType.ANNOTATION_TYPE})
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0087\u0002\u0018\u00002\u00020\u0001:\u0001\u0006B\u0011\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0007"}, d2 = {"Landroidx/annotation/RequiresOptIn;", "", "Landroidx/annotation/RequiresOptIn$Level;", "level", "<init>", "(Landroidx/annotation/RequiresOptIn$Level;)V", "Level", "annotation-experimental_release"}, k = 1, mv = {1, 7, 0}, xi = 48)
@d
@Retention(RetentionPolicy.CLASS)
@c
/* loaded from: classes.dex */
public @interface RequiresOptIn {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: RequiresOptIn.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001¨\u0006\u0002"}, d2 = {"Landroidx/annotation/RequiresOptIn$Level;", "", "annotation-experimental_release"}, k = 1, mv = {1, 7, 0}, xi = 48)
    public static final class Level {

        /* renamed from: b, reason: collision with root package name */
        public static final Level f21308b;

        /* renamed from: c, reason: collision with root package name */
        public static final /* synthetic */ Level[] f21309c;

        /* JADX INFO: Fake field, exist only in values array */
        Level EF0;

        static {
            Level level = new Level("WARNING", 0);
            Level level2 = new Level("ERROR", 1);
            f21308b = level2;
            f21309c = new Level[]{level, level2};
        }

        public Level() {
            throw null;
        }

        public static Level valueOf(String str) {
            return (Level) Enum.valueOf(Level.class, str);
        }

        public static Level[] values() {
            return (Level[]) f21309c.clone();
        }
    }
}
