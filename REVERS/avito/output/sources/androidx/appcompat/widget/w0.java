package androidx.appcompat.widget;

import android.R;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

/* compiled from: SuggestionsAdapter.java */
/* loaded from: classes.dex */
class w0 extends androidx.cursoradapter.widget.c implements View.OnClickListener {

    /* renamed from: z, reason: collision with root package name */
    public static final /* synthetic */ int f22639z = 0;

    /* renamed from: m, reason: collision with root package name */
    public final SearchView f22640m;

    /* renamed from: n, reason: collision with root package name */
    public final SearchableInfo f22641n;

    /* renamed from: o, reason: collision with root package name */
    public final Context f22642o;

    /* renamed from: p, reason: collision with root package name */
    public final WeakHashMap<String, Drawable.ConstantState> f22643p;

    /* renamed from: q, reason: collision with root package name */
    public final int f22644q;

    /* renamed from: r, reason: collision with root package name */
    public int f22645r;

    /* renamed from: s, reason: collision with root package name */
    public ColorStateList f22646s;

    /* renamed from: t, reason: collision with root package name */
    public int f22647t;

    /* renamed from: u, reason: collision with root package name */
    public int f22648u;

    /* renamed from: v, reason: collision with root package name */
    public int f22649v;

    /* renamed from: w, reason: collision with root package name */
    public int f22650w;

    /* renamed from: x, reason: collision with root package name */
    public int f22651x;

    /* renamed from: y, reason: collision with root package name */
    public int f22652y;

    /* compiled from: SuggestionsAdapter.java */
    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        public final TextView f22653a;

        /* renamed from: b, reason: collision with root package name */
        public final TextView f22654b;

        /* renamed from: c, reason: collision with root package name */
        public final ImageView f22655c;

        /* renamed from: d, reason: collision with root package name */
        public final ImageView f22656d;

        /* renamed from: e, reason: collision with root package name */
        public final ImageView f22657e;

        public a(View view) {
            this.f22653a = (TextView) view.findViewById(R.id.text1);
            this.f22654b = (TextView) view.findViewById(R.id.text2);
            this.f22655c = (ImageView) view.findViewById(R.id.icon1);
            this.f22656d = (ImageView) view.findViewById(R.id.icon2);
            this.f22657e = (ImageView) view.findViewById(com.avito.android.R.id.edit_query);
        }
    }

    public w0(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap<String, Drawable.ConstantState> weakHashMap) {
        super(context, searchView.getSuggestionRowLayout());
        this.f22645r = 1;
        this.f22647t = -1;
        this.f22648u = -1;
        this.f22649v = -1;
        this.f22650w = -1;
        this.f22651x = -1;
        this.f22652y = -1;
        this.f22640m = searchView;
        this.f22641n = searchableInfo;
        this.f22644q = searchView.getSuggestionCommitIconResId();
        this.f22642o = context;
        this.f22643p = weakHashMap;
    }

    public static String l(Cursor cursor, int i12) {
        if (i12 == -1) {
            return null;
        }
        try {
            return cursor.getString(i12);
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // androidx.cursoradapter.widget.a, androidx.cursoradapter.widget.b.a
    public final String a(Cursor cursor) {
        String strL;
        String strL2;
        if (cursor == null) {
            return null;
        }
        String strL3 = l(cursor, cursor.getColumnIndex("suggest_intent_query"));
        if (strL3 != null) {
            return strL3;
        }
        SearchableInfo searchableInfo = this.f22641n;
        if (searchableInfo.shouldRewriteQueryFromData() && (strL2 = l(cursor, cursor.getColumnIndex("suggest_intent_data"))) != null) {
            return strL2;
        }
        if (!searchableInfo.shouldRewriteQueryFromText() || (strL = l(cursor, cursor.getColumnIndex("suggest_text_1"))) == null) {
            return null;
        }
        return strL;
    }

    @Override // androidx.cursoradapter.widget.a, androidx.cursoradapter.widget.b.a
    public final void b(Cursor cursor) {
        try {
            super.b(cursor);
            if (cursor != null) {
                this.f22647t = cursor.getColumnIndex("suggest_text_1");
                this.f22648u = cursor.getColumnIndex("suggest_text_2");
                this.f22649v = cursor.getColumnIndex("suggest_text_2_url");
                this.f22650w = cursor.getColumnIndex("suggest_icon_1");
                this.f22651x = cursor.getColumnIndex("suggest_icon_2");
                this.f22652y = cursor.getColumnIndex("suggest_flags");
            }
        } catch (Exception unused) {
        }
    }

    @Override // androidx.cursoradapter.widget.a, androidx.cursoradapter.widget.b.a
    public final Cursor d(CharSequence charSequence) {
        String string = charSequence == null ? "" : charSequence.toString();
        SearchView searchView = this.f22640m;
        if (searchView.getVisibility() == 0 && searchView.getWindowVisibility() == 0) {
            try {
                Cursor cursorK = k(this.f22641n, string);
                if (cursorK != null) {
                    cursorK.getCount();
                    return cursorK;
                }
            } catch (RuntimeException unused) {
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0125  */
    @Override // androidx.cursoradapter.widget.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void e(android.view.View r22, android.database.Cursor r23) throws android.content.pm.PackageManager.NameNotFoundException, java.lang.NumberFormatException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 403
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.w0.e(android.view.View, android.database.Cursor):void");
    }

    @Override // androidx.cursoradapter.widget.c, androidx.cursoradapter.widget.a
    public final View g(Context context, Cursor cursor, ViewGroup viewGroup) {
        View viewG = super.g(context, cursor, viewGroup);
        viewG.setTag(new a(viewG));
        ((ImageView) viewG.findViewById(com.avito.android.R.id.edit_query)).setImageResource(this.f22644q);
        return viewG;
    }

    @Override // androidx.cursoradapter.widget.a, android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public final View getDropDownView(int i12, View view, ViewGroup viewGroup) {
        try {
            return super.getDropDownView(i12, view, viewGroup);
        } catch (RuntimeException e12) {
            View viewF = f(this.f22642o, this.f45341d, viewGroup);
            if (viewF != null) {
                ((a) viewF.getTag()).f22653a.setText(e12.toString());
            }
            return viewF;
        }
    }

    @Override // androidx.cursoradapter.widget.a, android.widget.Adapter
    public final View getView(int i12, View view, ViewGroup viewGroup) {
        try {
            return super.getView(i12, view, viewGroup);
        } catch (RuntimeException e12) {
            View viewG = g(this.f22642o, this.f45341d, viewGroup);
            ((a) viewG.getTag()).f22653a.setText(e12.toString());
            return viewG;
        }
    }

    @Override // androidx.cursoradapter.widget.a, android.widget.BaseAdapter, android.widget.Adapter
    public final boolean hasStableIds() {
        return false;
    }

    public final Drawable i(Uri uri) throws PackageManager.NameNotFoundException, NumberFormatException, FileNotFoundException {
        int identifier;
        String authority = uri.getAuthority();
        if (TextUtils.isEmpty(authority)) {
            throw new FileNotFoundException(androidx.appcompat.app.r.n(uri, "No authority: "));
        }
        try {
            Resources resourcesForApplication = this.f22642o.getPackageManager().getResourcesForApplication(authority);
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments == null) {
                throw new FileNotFoundException(androidx.appcompat.app.r.n(uri, "No path: "));
            }
            int size = pathSegments.size();
            if (size == 1) {
                try {
                    identifier = Integer.parseInt(pathSegments.get(0));
                } catch (NumberFormatException unused) {
                    throw new FileNotFoundException(androidx.appcompat.app.r.n(uri, "Single path segment is not a resource ID: "));
                }
            } else {
                if (size != 2) {
                    throw new FileNotFoundException(androidx.appcompat.app.r.n(uri, "More than two path segments: "));
                }
                identifier = resourcesForApplication.getIdentifier(pathSegments.get(1), pathSegments.get(0), authority);
            }
            if (identifier != 0) {
                return resourcesForApplication.getDrawable(identifier);
            }
            throw new FileNotFoundException(androidx.appcompat.app.r.n(uri, "No resource found for: "));
        } catch (PackageManager.NameNotFoundException unused2) {
            throw new FileNotFoundException(androidx.appcompat.app.r.n(uri, "No package found for authority: "));
        }
    }

    public final Drawable j(String str) throws PackageManager.NameNotFoundException, NumberFormatException, IOException {
        WeakHashMap<String, Drawable.ConstantState> weakHashMap = this.f22643p;
        Context context = this.f22642o;
        Drawable drawableI = null;
        if (str != null && !str.isEmpty() && !"0".equals(str)) {
            try {
                int i12 = Integer.parseInt(str);
                String str2 = "android.resource://" + context.getPackageName() + "/" + i12;
                Drawable.ConstantState constantState = weakHashMap.get(str2);
                Drawable drawableNewDrawable = constantState == null ? null : constantState.newDrawable();
                if (drawableNewDrawable != null) {
                    return drawableNewDrawable;
                }
                Drawable drawable = androidx.core.content.d.getDrawable(context, i12);
                if (drawable != null) {
                    weakHashMap.put(str2, drawable.getConstantState());
                }
                return drawable;
            } catch (Resources.NotFoundException unused) {
                return null;
            } catch (NumberFormatException unused2) {
                Drawable.ConstantState constantState2 = weakHashMap.get(str);
                Drawable drawableNewDrawable2 = constantState2 == null ? null : constantState2.newDrawable();
                if (drawableNewDrawable2 != null) {
                    return drawableNewDrawable2;
                }
                Uri uri = Uri.parse(str);
                try {
                    if ("android.resource".equals(uri.getScheme())) {
                        try {
                            drawableI = i(uri);
                        } catch (Resources.NotFoundException unused3) {
                            throw new FileNotFoundException("Resource does not exist: " + uri);
                        }
                    } else {
                        InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
                        if (inputStreamOpenInputStream == null) {
                            throw new FileNotFoundException("Failed to open " + uri);
                        }
                        try {
                            drawableI = Drawable.createFromStream(inputStreamOpenInputStream, null);
                        } finally {
                            try {
                                inputStreamOpenInputStream.close();
                            } catch (IOException unused4) {
                                uri.toString();
                            }
                        }
                    }
                } catch (FileNotFoundException e12) {
                    Objects.toString(uri);
                    e12.getMessage();
                }
                if (drawableI != null) {
                    weakHashMap.put(str, drawableI.getConstantState());
                }
            }
        }
        return drawableI;
    }

    public final Cursor k(SearchableInfo searchableInfo, String str) {
        String suggestAuthority;
        String[] strArr = null;
        if (searchableInfo == null || (suggestAuthority = searchableInfo.getSuggestAuthority()) == null) {
            return null;
        }
        Uri.Builder builderFragment = new Uri.Builder().scheme("content").authority(suggestAuthority).query("").fragment("");
        String suggestPath = searchableInfo.getSuggestPath();
        if (suggestPath != null) {
            builderFragment.appendEncodedPath(suggestPath);
        }
        builderFragment.appendPath("search_suggest_query");
        String suggestSelection = searchableInfo.getSuggestSelection();
        if (suggestSelection != null) {
            strArr = new String[]{str};
        } else {
            builderFragment.appendPath(str);
        }
        String[] strArr2 = strArr;
        builderFragment.appendQueryParameter("limit", String.valueOf(50));
        return this.f22642o.getContentResolver().query(builderFragment.build(), null, suggestSelection, strArr2, null);
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Cursor cursor = this.f45341d;
        Bundle extras = cursor != null ? cursor.getExtras() : null;
        if (extras != null) {
            extras.getBoolean("in_progress");
        }
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        Cursor cursor = this.f45341d;
        Bundle extras = cursor != null ? cursor.getExtras() : null;
        if (extras != null) {
            extras.getBoolean("in_progress");
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        Object tag = view.getTag();
        if (tag instanceof CharSequence) {
            this.f22640m.t((CharSequence) tag);
        }
    }
}
