package sardari.sample

import android.app.Application
import sardari.utils.FontUtils

class MyApp : Application()
{
    override fun onCreate()
    {
        super.onCreate()

        FontUtils.init(this,"IranYekanBoldFN")
//        FontUtils.init(this)
    }
}