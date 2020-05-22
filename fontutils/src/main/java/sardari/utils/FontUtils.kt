package sardari.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.text.*
import android.text.style.TypefaceSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.collection.SimpleArrayMap
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputLayout

class FontUtils {
    companion object {
        private const val PATH = "fonts/%s.ttf"
        private const val DEFAULT_FONT_NAME = "IRANSansMobile"

        const val IRANSansMobile = "IRANSansMobile"
        const val IRANSansMobile_Bold = "IRANSansMobile_Bold"
        const val IRANSansMobile_Light = "IRANSansMobile_Light"
        const val IRANSansMobile_Medium = "IRANSansMobile_Medium"
        const val IRANSansMobile_UltraLight = "IRANSansMobile_UltraLight"

        private lateinit var font: Font
        private var context: Context? = null
        private val cache = SimpleArrayMap<String, Typeface>()
        private lateinit var typeface: Typeface

        private fun getFontFromCache(fontName: String): Typeface {
            try {
                if (context != null) {
                    synchronized(cache) {
                        val path = String.format(PATH, fontName)
                        if (!cache.containsKey(fontName)) {
                            val t = Typeface.createFromAsset(context?.assets, path)
                            cache.put(fontName, t)
                            return t
                        }
                        return cache.get(fontName)!!
                    }
                } else {
                    return Typeface.DEFAULT
                }
            } catch (e: Exception) {
                return Typeface.DEFAULT
            }
        }

        @JvmOverloads
        @JvmStatic
        fun init(context: Context, fontName: String? = null) {
            this.context = context
            this.typeface = getFontFromCache(fontName ?: DEFAULT_FONT_NAME)

            font = Font(typeface)
        }

        @JvmStatic
        fun setDefaultFontName(fontName: String) {
            try {
                typeface = getFontFromCache(fontName)
                font.setDefaultTypeface(typeface)
            } catch (e: Exception) {

            }
        }

        @JvmStatic
        fun getTypeface(): Typeface {
            return typeface
        }

        @JvmOverloads
        @JvmStatic
        fun setView(view: Any, fontName: String? = null) {
            try {
                val typeface = fontName?.let { getFontFromCache(it) }

                when (view) {
                    is View -> {
                        font.setView(view, typeface)
                    }
                    is TextView -> {
                        font.setView(view, typeface)
                    }
                    is TextInputLayout -> {
                        font.setView(view, typeface)
                    }
                    is NavigationView -> {
                        font.setView(view, typeface)
                    }
                }
            } catch (e: Exception) {

            }
        }

        @JvmOverloads
        @JvmStatic
        fun setText(text: String, fontName: String? = null): SpannableStringBuilder {
            return try {
                val typeface = fontName?.let { getFontFromCache(it) }

                font.setText(text, typeface)
            } catch (e: Exception) {
                val s = SpannableStringBuilder(text)
                s.setSpan(
                    CustomTypefaceSpan("", typeface),
                    0,
                    text.length,
                    Spanned.SPAN_EXCLUSIVE_INCLUSIVE
                )

                return s

            }
        }
    }

    internal class Font constructor(private var tf: Typeface) {
        fun setDefaultTypeface(typeface: Typeface) {
            tf = typeface
        }

        fun setView(view: View, typeface: Typeface? = null) {
            try {
                if (view is ViewGroup && view !is TextInputLayout) {
                    for (i in 0 until view.childCount) {
                        val child = view.getChildAt(i)
                        setView(child)
                    }
                } else if (view is TextView) {
                    view.typeface = typeface ?: tf
                } else if (view is TextInputLayout) {
                    view.typeface = typeface ?: tf
                    val vg = view as ViewGroup
                    val child = vg.getChildAt(0)
                    setView(child)
                }
            } catch (ignored: Exception) {
            }
        }

        fun setView(view: TextView, typeface: Typeface? = null) {
            view.typeface = typeface ?: tf
        }

        fun setView(view: TextInputLayout, typeface: Typeface? = null) {
            view.typeface = typeface ?: tf
            val child: View = view.getChildAt(0)
            setView(child)
        }

        fun setView(nav: NavigationView, typeface: Typeface? = null) {
            val m: Menu = nav.menu
            for (i in 0 until m.size()) {
                val mi = m.getItem(i)

                //for applying a font to subMenu ...
                val subMenu = mi.subMenu
                if (subMenu != null && subMenu.size() > 0) {
                    for (j in 0 until subMenu.size()) {
                        val subMenuItem = subMenu.getItem(j)
                        menuItem(subMenuItem, typeface ?: tf)
                    }
                }

                //the method we have create in activity
                menuItem(mi, typeface ?: tf)
            }
        }

        private fun menuItem(mi: MenuItem, typeface: Typeface? = null) {
            val mNewTitle = SpannableString(mi.title)
            mNewTitle.setSpan(
                CustomTypefaceSpan("", typeface ?: tf),
                0,
                mNewTitle.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            mi.title = mNewTitle
        }

        fun setText(string: String, typeface: Typeface? = null): SpannableStringBuilder {
            val s = SpannableStringBuilder(string)
            s.setSpan(
                CustomTypefaceSpan("", typeface ?: tf),
                0,
                string.length,
                Spanned.SPAN_EXCLUSIVE_INCLUSIVE
            )

            return s
        }
    }

    internal class CustomTypefaceSpan(family: String?, private val newType: Typeface) : TypefaceSpan(family) {
        private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
            val oldStyle: Int
            val old = paint.typeface
            oldStyle = old?.style ?: 0
            val fake = oldStyle and tf.style.inv()

            if (fake and Typeface.BOLD != 0) {
                paint.isFakeBoldText = true
            }

            if (fake and Typeface.ITALIC != 0) {
                paint.textSkewX = -0.25f
            }

            paint.typeface = tf
        }

        override fun updateDrawState(ds: TextPaint) {
            applyCustomTypeFace(ds, newType)
        }

        override fun updateMeasureState(paint: TextPaint) {
            applyCustomTypeFace(paint, newType)
        }
    }
}