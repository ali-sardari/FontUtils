package sardari.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import sardari.utils.FontUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FontUtils.setView(tv1)
        FontUtils.setDefaultFontName(FontUtils.IRANSansMobile_Bold)

        FontUtils.setView(tv2)
        FontUtils.setView(tv3, FontUtils.IRANSansMobile_Light)
        FontUtils.setView(tv4)

        FontUtils.setDefaultFontName("IranYekanBoldFN")
        FontUtils.setView(llFontGroup,FontUtils.IRANSansMobile_Medium) //Apply on ViewGroup

        val str = FontUtils.setText("تست SpannableStringBuilder")
        tv5.text = str

    }
}

