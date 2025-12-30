package AD;

import Y61.k;
import com.akita.compose.theme.re23.style.C0;
import com.avito.android.A0;
import com.avito.android.ownership.Owners;
import kotlin.Metadata;
import kotlin.jvm.internal.h0;
import kotlin.jvm.internal.m0;
import kotlin.jvm.internal.n0;
import kotlin.reflect.n;

/* compiled from: FavoritesFeatures.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"LAD/a;", "Lcom/avito/android/A0;", "<init>", "()V", "_avito-discouraged_avito-feature_favorites"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes13.dex */
public final class a extends A0 {

    /* renamed from: k, reason: collision with root package name */
    public static final /* synthetic */ n<Object>[] f216k;

    /* renamed from: b, reason: collision with root package name */
    @k
    public final A0.a f217b;

    /* renamed from: c, reason: collision with root package name */
    @k
    public final A0.a f218c;

    /* renamed from: d, reason: collision with root package name */
    @k
    public final A0.a f219d;

    /* renamed from: e, reason: collision with root package name */
    @k
    public final A0.a f220e;

    /* renamed from: f, reason: collision with root package name */
    @k
    public final A0.a f221f;

    /* renamed from: g, reason: collision with root package name */
    @k
    public final A0.a f222g;

    /* renamed from: h, reason: collision with root package name */
    @k
    public final A0.a f223h;

    /* renamed from: i, reason: collision with root package name */
    @k
    public final A0.a f224i;

    /* renamed from: j, reason: collision with root package name */
    @k
    public final A0.a f225j;

    static {
        h0 h0Var = new h0(a.class, "appRaterOnFavoriteAdd", "getAppRaterOnFavoriteAdd()Lcom/avito/android/toggle/Feature;", 0);
        n0 n0Var = m0.f406844a;
        f216k = new n[]{n0Var.i(h0Var), C0.k(a.class, "favoriteUxFeedback", "getFavoriteUxFeedback()Lcom/avito/android/toggle/Feature;", 0, n0Var), C0.k(a.class, "favoriteInMemoryCache", "getFavoriteInMemoryCache()Lcom/avito/android/toggle/Feature;", 0, n0Var), C0.k(a.class, "afbSpaceFavoritesCollections", "getAfbSpaceFavoritesCollections()Lcom/avito/android/toggle/Feature;", 0, n0Var), C0.k(a.class, "afbSpaceFavoritesComparsion", "getAfbSpaceFavoritesComparsion()Lcom/avito/android/toggle/Feature;", 0, n0Var), C0.k(a.class, "afbSpaceFavoritesSellers", "getAfbSpaceFavoritesSellers()Lcom/avito/android/toggle/Feature;", 0, n0Var), C0.k(a.class, "business360NavbarFavsClear", "getBusiness360NavbarFavsClear()Lcom/avito/android/toggle/Feature;", 0, n0Var), C0.k(a.class, "showFavoritesNewToast", "getShowFavoritesNewToast()Lcom/avito/android/toggle/Feature;", 0, n0Var), C0.k(a.class, "favoritesLinkAppendingCategoryIdFix", "getFavoritesLinkAppendingCategoryIdFix()Lcom/avito/android/toggle/Feature;", 0, n0Var), C0.k(a.class, "showFavoritesCategoryTabOnboarding", "getShowFavoritesCategoryTabOnboarding()Lcom/avito/android/toggle/Feature;", 0, n0Var)};
    }

    public a() {
        Owners owners = Owners.f210397D;
        Boolean bool = Boolean.FALSE;
        A0.u(this, "Показ диалога оценить приложение после добавления в избранное", "appRaterOnFavoriteAdd", bool, false, 0, owners, 56);
        Boolean bool2 = Boolean.TRUE;
        this.f217b = A0.u(this, "Опросы по использованию избранного", "favoriteUxFeedback", bool2, false, 0, owners, 56);
        this.f218c = A0.u(this, "use in memory cache instead persistent", "favoriteInMemoryCache", bool2, false, 0, Owners.f210394C, 56);
        Owners owners2 = Owners.f210550w;
        this.f219d = A0.u(this, "Отображение вкладки 'Подборки' в Избранном в Бизнес360", "afbSpaceFavoritesCollections", bool, false, 0, owners2, 56);
        this.f220e = A0.u(this, "Отображение вкладки 'Сравнение' в Избранном в Бизнес360", "afbSpaceFavoritesComparsion", bool, false, 0, owners2, 56);
        this.f221f = A0.u(this, "Отображение вкладки 'Профили' в Избранном в Бизнес360", "afbSpaceFavoritesSellers", bool, false, 0, owners2, 56);
        this.f222g = A0.u(this, "Показать иконку 'три точки' в избранном с навбаром Б360", "business360_navbar_favs_clear", bool2, false, 0, owners2, 56);
        Owners owners3 = Owners.f210405F3;
        this.f223h = A0.u(this, "Показывать новый тост лайка айтема с данными с бекенда", "showFavoritesNewToast", bool2, false, 0, owners3, 56);
        this.f224i = A0.u(this, "Фикс обработки выбранного таба при переходе в избранное по диплинку", "favoritesLinkAppendingCategoryIdFix", bool2, false, 0, owners3, 56);
        this.f225j = A0.u(this, "Показывать тултип-онбординг для вкладок категорий в избранном", "showFavoritesCategoryTabOnboarding", bool2, false, 0, owners3, 56);
    }
}
