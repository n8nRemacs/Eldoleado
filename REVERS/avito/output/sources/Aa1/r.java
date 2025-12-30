package aa1;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.C42756l;
import za1.C50538c;

/* loaded from: classes9.dex */
public final class r {

    /* renamed from: a, reason: collision with root package name */
    public final wa1.c f20980a;

    /* renamed from: b, reason: collision with root package name */
    public final ArrayList f20981b;

    /* renamed from: c, reason: collision with root package name */
    public final int[] f20982c;

    /* renamed from: d, reason: collision with root package name */
    public final ArrayList f20983d;

    /* renamed from: e, reason: collision with root package name */
    public final int[] f20984e;

    public r(wa1.c cVar, wa1.d dVar) {
        this.f20980a = cVar;
        ArrayList arrayList = new ArrayList(4);
        for (int i12 = 0; i12 < 4; i12++) {
            arrayList.add(new int[2]);
        }
        this.f20981b = arrayList;
        this.f20982c = new int[4];
        ArrayList arrayList2 = new ArrayList(4);
        for (int i13 = 0; i13 < 4; i13++) {
            arrayList2.add(new int[2]);
        }
        this.f20983d = arrayList2;
        this.f20984e = new int[4];
    }

    public static void b(int[] iArr, int i12, int[] iArr2, int i13, int i14, ArrayList arrayList, int[] iArr3) {
        if (i12 == 1) {
            iArr3[i14] = i12;
            ((int[]) arrayList.get(i14))[0] = iArr[0];
            ((int[]) arrayList.get(i14))[1] = iArr[1];
            return;
        }
        if (i13 == 1) {
            iArr3[i14] = i13;
            ((int[]) arrayList.get(i14))[0] = iArr2[0];
            ((int[]) arrayList.get(i14))[1] = iArr2[1];
            return;
        }
        if (i12 == 2 && i13 == 0) {
            iArr3[i14] = i12;
            ((int[]) arrayList.get(i14))[0] = iArr[0];
            ((int[]) arrayList.get(i14))[1] = iArr[1];
            return;
        }
        if (i12 == 0 && i13 == 2) {
            iArr3[i14] = i13;
            ((int[]) arrayList.get(i14))[0] = iArr2[0];
            ((int[]) arrayList.get(i14))[1] = iArr2[1];
        } else if (i12 == 2 && i13 == 2) {
            iArr3[i14] = i12;
            ((int[]) arrayList.get(i14))[0] = (iArr[0] + iArr2[0]) / 2;
            ((int[]) arrayList.get(i14))[1] = (iArr[1] + iArr2[1]) / 2;
        } else if (i12 == 0 && i13 == 0) {
            iArr3[i14] = i13;
            ((int[]) arrayList.get(i14))[0] = iArr[0];
            ((int[]) arrayList.get(i14))[1] = iArr[1];
        }
    }

    public static boolean c(ArrayList arrayList, int[] iArr, int[] iArr2, ArrayList arrayList2, int[] iArr3) {
        kotlin.ranges.k it = kotlin.ranges.s.r(0, 4).iterator();
        int i12 = 0;
        while (it.f406910d) {
            int iA2 = it.a();
            int i13 = iArr2[iA2];
            ((int[]) arrayList2.get(iA2))[0] = ((int[]) arrayList.get(i13))[0];
            ((int[]) arrayList2.get(iA2))[1] = ((int[]) arrayList.get(i13))[1];
            int i14 = iArr[i13];
            iArr3[iA2] = i14;
            if (i14 == 1) {
                i12++;
            }
        }
        if (i12 == 2) {
            int[] iArr4 = new int[2];
            int i15 = 0;
            for (int i16 = 0; i16 < 4; i16++) {
                if (iArr3[i16] == 0) {
                    iArr4[i15] = i16;
                    if (i15 >= 1) {
                        break;
                    }
                    i15++;
                }
            }
            int i17 = iArr4[1];
            int i18 = iArr4[0];
            boolean z12 = i17 - i18 == 1;
            if (i18 == 0 && i17 == 3) {
                iArr4[0] = 3;
                iArr4[1] = 0;
                z12 = true;
            }
            if (z12) {
                int i19 = iArr4[0];
                float f12 = i19 % 2 == 0 ? 0.704f : 1.4204545f;
                int i22 = (i19 + 2) % 4;
                int i23 = (i22 + 5) % 4;
                int i24 = (i23 + 5) % 4;
                int i25 = (i24 + 5) % 4;
                float f13 = ((int[]) arrayList2.get(i23))[0] - ((int[]) arrayList2.get(i22))[0];
                float f14 = ((int[]) arrayList2.get(i23))[1] - ((int[]) arrayList2.get(i22))[1];
                iArr3[i24] = 2;
                ((int[]) arrayList2.get(i24))[0] = (int) (((int[]) arrayList2.get(i23))[0] + ((-f14) * f12));
                ((int[]) arrayList2.get(i24))[1] = (int) (((int[]) arrayList2.get(i23))[1] + (f12 * f13));
                iArr3[i25] = 2;
                ((int[]) arrayList2.get(i25))[0] = (int) (((int[]) arrayList2.get(i24))[0] - f13);
                ((int[]) arrayList2.get(i25))[1] = (int) (((int[]) arrayList2.get(i24))[1] - f14);
            } else if (iArr4[0] == 0) {
                int i26 = ((int[]) arrayList2.get(3))[0];
                int i27 = ((int[]) arrayList2.get(1))[1];
                int i28 = ((int[]) arrayList2.get(1))[0];
                int i29 = ((int[]) arrayList2.get(3))[1];
                iArr3[0] = 2;
                ((int[]) arrayList2.get(0))[0] = i26;
                ((int[]) arrayList2.get(0))[1] = i27;
                iArr3[2] = 2;
                ((int[]) arrayList2.get(2))[0] = i28;
                ((int[]) arrayList2.get(2))[1] = i29;
            } else {
                int i32 = ((int[]) arrayList2.get(0))[0];
                int i33 = ((int[]) arrayList2.get(0))[1];
                int i34 = ((int[]) arrayList2.get(2))[0];
                int i35 = ((int[]) arrayList2.get(2))[1];
                iArr3[1] = 2;
                ((int[]) arrayList2.get(1))[0] = i34;
                ((int[]) arrayList2.get(1))[1] = i33;
                iArr3[3] = 2;
                ((int[]) arrayList2.get(3))[0] = i32;
                ((int[]) arrayList2.get(3))[1] = i35;
            }
        } else if (i12 == 3) {
            int i36 = 0;
            while (true) {
                if (i36 >= 4) {
                    i36 = -1;
                    break;
                }
                if (iArr3[i36] == 0) {
                    break;
                }
                i36++;
            }
            int i37 = (i36 + 3) % 4;
            int i38 = (i36 + 5) % 4;
            int i39 = (i36 + 2) % 4;
            iArr3[i36] = 2;
            ((int[]) arrayList2.get(i36))[0] = (((int[]) arrayList2.get(i37))[0] + ((int[]) arrayList2.get(i38))[0]) - ((int[]) arrayList2.get(i39))[0];
            ((int[]) arrayList2.get(i36))[1] = (((int[]) arrayList2.get(i37))[1] + ((int[]) arrayList2.get(i38))[1]) - ((int[]) arrayList2.get(i39))[1];
        } else if (i12 != 4) {
            return false;
        }
        return true;
    }

    public final q a(float f12, float f13, float f14, float f15, FloatBuffer floatBuffer) {
        int i12;
        boolean z12;
        float[] fArr;
        wa1.c cVar;
        int[] iArr;
        float[] fArr2;
        float[] fArr3;
        int[] iArr2;
        int i13 = 6;
        ArrayList arrayList = new ArrayList(6);
        int i14 = 0;
        for (int i15 = 0; i15 < 6; i15++) {
            arrayList.add(new int[2]);
        }
        int[] iArr3 = new int[6];
        float[] fArrArray = floatBuffer.array();
        kotlin.ranges.k it = kotlin.ranges.s.r(0, 6).iterator();
        while (it.f406910d) {
            int iA2 = it.a();
            iArr3[iA2] = i14;
            wa1.c cVar2 = this.f20980a;
            int[] iArr4 = cVar2.f441580b;
            kotlin.ranges.k it2 = C42756l.D(iArr4).iterator();
            while (it2.f406910d) {
                iArr4[it2.a()] = i14;
            }
            kotlin.ranges.k it3 = kotlin.ranges.s.r(i14, cVar2.f441579a).iterator();
            float f16 = 0.0f;
            float f17 = -1.0f;
            int i16 = i14;
            int i17 = i16;
            float f18 = 0.0f;
            float f19 = 0.0f;
            int i18 = 1;
            int i19 = 1;
            while (it3.f406910d) {
                int iA3 = it3.a();
                float f22 = fArrArray[(i13 * iA3) + iA2];
                if (iArr4[iA3] != 0 || f22 < 0.3f) {
                    i13 = i13;
                    it = it;
                    it3 = it3;
                    cVar2 = cVar2;
                    fArrArray = fArrArray;
                    iArr4 = iArr4;
                    i14 = 0;
                    f16 = 0.0f;
                } else {
                    iArr4[iA3] = i18;
                    float[] fArr4 = cVar2.f441582d;
                    float f23 = fArr4[iA3];
                    float[] fArr5 = cVar2.f441583e;
                    float f24 = fArr5[iA3];
                    float f25 = fArrArray[iA2 + iA3];
                    float f26 = (f23 * f25) + f16;
                    float f27 = (f24 * f25) + f16;
                    float f28 = f25 + f16;
                    List list = cVar2.f441581c;
                    kotlin.ranges.k kVar = it;
                    ((C50538c) list.get(i16)).f444087b = i14;
                    C50538c c50538c = (C50538c) list.get(i16);
                    int[] iArr5 = c50538c.f444086a;
                    kotlin.ranges.k kVar2 = it3;
                    int i22 = c50538c.f444087b;
                    iArr5[i22] = iA3;
                    c50538c.f444087b = i22 + 1;
                    int i23 = i19;
                    while (true) {
                        ((C50538c) list.get(i23)).f444087b = 0;
                        kotlin.ranges.k it4 = kotlin.ranges.s.r(0, ((C50538c) list.get(i16)).f444087b).iterator();
                        while (it4.f406910d) {
                            int[] iArr6 = (int[]) cVar2.f441584f.get(((C50538c) list.get(i16)).f444086a[it4.a()]);
                            int length = iArr6.length;
                            wa1.c cVar3 = cVar2;
                            int i24 = 0;
                            while (i24 < length) {
                                int i25 = iArr6[i24];
                                float f29 = fArrArray[(6 * i25) + iA2];
                                if (iArr4[i25] != 0 || f29 < 0.3f) {
                                    fArr2 = fArr5;
                                    fArr3 = fArrArray;
                                    iArr2 = iArr4;
                                } else {
                                    iArr4[i25] = i18;
                                    float f32 = (fArr4[i25] * f29) + f26;
                                    float f33 = (fArr5[i25] * f29) + f27;
                                    f28 += f29;
                                    fArr2 = fArr5;
                                    C50538c c50538c2 = (C50538c) list.get(i23);
                                    fArr3 = fArrArray;
                                    int[] iArr7 = c50538c2.f444086a;
                                    iArr2 = iArr4;
                                    int i26 = c50538c2.f444087b;
                                    iArr7[i26] = i25;
                                    c50538c2.f444087b = i26 + 1;
                                    f26 = f32;
                                    f27 = f33;
                                }
                                i24++;
                                fArr5 = fArr2;
                                fArrArray = fArr3;
                                iArr4 = iArr2;
                            }
                            cVar2 = cVar3;
                        }
                        float[] fArr6 = fArr5;
                        fArr = fArrArray;
                        cVar = cVar2;
                        iArr = iArr4;
                        if (((C50538c) list.get(i23)).f444087b == 0) {
                            break;
                        }
                        i16 = 1 - i16;
                        i23 = 1 - i23;
                        cVar2 = cVar;
                        fArr5 = fArr6;
                        fArrArray = fArr;
                        iArr4 = iArr;
                    }
                    float f34 = f26 / f28;
                    float f35 = f27 / f28;
                    if (f28 > f17) {
                        f18 = f34;
                        f19 = f35;
                        f17 = f28;
                    }
                    i18++;
                    i19 = i23;
                    i13 = 6;
                    it = kVar;
                    it3 = kVar2;
                    cVar2 = cVar;
                    fArrArray = fArr;
                    iArr4 = iArr;
                    i14 = 0;
                    f16 = 0.0f;
                    i17 = 1;
                }
            }
            int i27 = i13;
            float[] fArr7 = fArrArray;
            kotlin.ranges.k kVar3 = it;
            if (i17 != 0) {
                iArr3[iA2] = 1;
                float f36 = 16;
                ((int[]) arrayList.get(iA2))[0] = (int) (((((f18 + 0.5f) * f36) - 0.5f) - f14) * f12);
                ((int[]) arrayList.get(iA2))[1] = (int) (((((f19 + 0.5f) * f36) - 0.5f) - f15) * f13);
            }
            i13 = i27;
            it = kVar3;
            fArrArray = fArr7;
            i14 = 0;
        }
        int[] iArr8 = wa1.s.f441624a;
        ArrayList arrayList2 = this.f20981b;
        int[] iArr9 = this.f20982c;
        boolean zC2 = c(arrayList, iArr3, iArr8, arrayList2, iArr9);
        int[] iArr10 = wa1.r.f441621a;
        ArrayList arrayList3 = this.f20983d;
        int[] iArr11 = this.f20984e;
        boolean zC3 = c(arrayList, iArr3, iArr10, arrayList3, iArr11);
        if (zC2 && zC3) {
            i12 = 0;
            z12 = true;
        } else {
            i12 = 0;
            z12 = false;
        }
        iArr3[i12] = iArr9[i12];
        ((int[]) arrayList.get(i12))[i12] = ((int[]) arrayList2.get(i12))[i12];
        ((int[]) arrayList.get(i12))[1] = ((int[]) arrayList2.get(i12))[1];
        iArr3[1] = iArr9[1];
        ((int[]) arrayList.get(1))[i12] = ((int[]) arrayList2.get(1))[i12];
        ((int[]) arrayList.get(1))[1] = ((int[]) arrayList2.get(1))[1];
        b((int[]) arrayList2.get(3), iArr9[3], (int[]) arrayList3.get(i12), iArr11[i12], 5, arrayList, iArr3);
        b((int[]) arrayList2.get(2), iArr9[2], (int[]) arrayList3.get(1), iArr11[1], 2, arrayList, iArr3);
        iArr3[3] = iArr11[2];
        ((int[]) arrayList.get(3))[0] = ((int[]) arrayList3.get(2))[0];
        ((int[]) arrayList.get(3))[1] = ((int[]) arrayList3.get(2))[1];
        iArr3[4] = iArr11[3];
        ((int[]) arrayList.get(4))[0] = ((int[]) arrayList3.get(3))[0];
        ((int[]) arrayList.get(4))[1] = ((int[]) arrayList3.get(3))[1];
        return new q(z12, arrayList, zC2, zC3);
    }
}
