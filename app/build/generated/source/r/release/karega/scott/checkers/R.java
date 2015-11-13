/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * aapt tool from the resource data it found.  It
 * should not be modified by hand.
 */

package karega.scott.checkers;

public final class R {
    public static final class attr {
        /** <p>Must be a color value, in the form of "<code>#<i>rgb</i></code>", "<code>#<i>argb</i></code>",
"<code>#<i>rrggbb</i></code>", or "<code>#<i>aarrggbb</i></code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static final int borderColor=0x7f010002;
        /** <p>Must be a color value, in the form of "<code>#<i>rgb</i></code>", "<code>#<i>argb</i></code>",
"<code>#<i>rrggbb</i></code>", or "<code>#<i>aarrggbb</i></code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
         */
        public static final int fillColor=0x7f010001;
        /** <p>Must be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>EMPTY</code></td><td>0</td><td></td></tr>
<tr><td><code>PLAYER1</code></td><td>1</td><td></td></tr>
<tr><td><code>PLAYER2</code></td><td>2</td><td></td></tr>
<tr><td><code>LOCKED</code></td><td>3</td><td></td></tr>
</table>
         */
        public static final int stateType=0x7f010000;
    }
    public static final class drawable {
        public static final int ic_launcher=0x7f020000;
        public static final int screenshot1=0x7f020001;
        public static final int screenshot2=0x7f020002;
        public static final int screenshot3=0x7f020003;
    }
    public static final class id {
        public static final int EMPTY=0x7f070000;
        public static final int LOCKED=0x7f070001;
        public static final int PLAYER1=0x7f070002;
        public static final int PLAYER2=0x7f070003;
        public static final int boardGame=0x7f070004;
        public static final int exitGame=0x7f070006;
        public static final int menu_settings=0x7f07000b;
        public static final int newGame=0x7f070005;
        public static final int splashText=0x7f070007;
        public static final int squareView=0x7f07000a;
        public static final int startPhonePlay=0x7f070009;
        public static final int startPlayNPass=0x7f070008;
    }
    public static final class layout {
        public static final int activity_board=0x7f030000;
        public static final int activity_main=0x7f030001;
        public static final int board_square=0x7f030002;
    }
    public static final class menu {
        public static final int activity_board=0x7f060000;
        public static final int activity_main=0x7f060001;
        public static final int activity_test=0x7f060002;
    }
    public static final class string {
        public static final int app_description=0x7f050000;
        public static final int app_name=0x7f050001;
        public static final int exit_game=0x7f050002;
        public static final int menu_settings=0x7f050003;
        public static final int new_game=0x7f050004;
        public static final int splash_text_view=0x7f050005;
        public static final int start_phone_play=0x7f050006;
        public static final int start_play_n_pass=0x7f050007;
        public static final int title_activity_board=0x7f050008;
        public static final int title_activity_test=0x7f050009;
    }
    public static final class style {
        /**  API 11 theme customizations can go here. 
 API 14 theme customizations can go here. 

            Theme customizations available in newer API levels can go in
            res/values-vXX/styles.xml, while customizations related to
            backward-compatibility can go here.
        
         */
        public static final int AppBaseTheme=0x7f040000;
        /**  All customizations that are NOT specific to a particular API-level can go here. 
         */
        public static final int AppTheme=0x7f040001;
    }
    public static final class styleable {
        /** Attributes that can be used with a SquareView.
           <p>Includes the following attributes:</p>
           <table>
           <colgroup align="left" />
           <colgroup align="left" />
           <tr><th>Attribute</th><th>Description</th></tr>
           <tr><td><code>{@link #SquareView_borderColor karega.scott.checkers:borderColor}</code></td><td></td></tr>
           <tr><td><code>{@link #SquareView_fillColor karega.scott.checkers:fillColor}</code></td><td></td></tr>
           <tr><td><code>{@link #SquareView_stateType karega.scott.checkers:stateType}</code></td><td></td></tr>
           </table>
           @see #SquareView_borderColor
           @see #SquareView_fillColor
           @see #SquareView_stateType
         */
        public static final int[] SquareView = {
            0x7f010000, 0x7f010001, 0x7f010002
        };
        /**
          <p>This symbol is the offset where the {@link karega.scott.checkers.R.attr#borderColor}
          attribute's value can be found in the {@link #SquareView} array.


          <p>Must be a color value, in the form of "<code>#<i>rgb</i></code>", "<code>#<i>argb</i></code>",
"<code>#<i>rrggbb</i></code>", or "<code>#<i>aarrggbb</i></code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          @attr name karega.scott.checkers:borderColor
        */
        public static final int SquareView_borderColor = 2;
        /**
          <p>This symbol is the offset where the {@link karega.scott.checkers.R.attr#fillColor}
          attribute's value can be found in the {@link #SquareView} array.


          <p>Must be a color value, in the form of "<code>#<i>rgb</i></code>", "<code>#<i>argb</i></code>",
"<code>#<i>rrggbb</i></code>", or "<code>#<i>aarrggbb</i></code>".
<p>This may also be a reference to a resource (in the form
"<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
theme attribute (in the form
"<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
containing a value of this type.
          @attr name karega.scott.checkers:fillColor
        */
        public static final int SquareView_fillColor = 1;
        /**
          <p>This symbol is the offset where the {@link karega.scott.checkers.R.attr#stateType}
          attribute's value can be found in the {@link #SquareView} array.


          <p>Must be one of the following constant values.</p>
<table>
<colgroup align="left" />
<colgroup align="left" />
<colgroup align="left" />
<tr><th>Constant</th><th>Value</th><th>Description</th></tr>
<tr><td><code>EMPTY</code></td><td>0</td><td></td></tr>
<tr><td><code>PLAYER1</code></td><td>1</td><td></td></tr>
<tr><td><code>PLAYER2</code></td><td>2</td><td></td></tr>
<tr><td><code>LOCKED</code></td><td>3</td><td></td></tr>
</table>
          @attr name karega.scott.checkers:stateType
        */
        public static final int SquareView_stateType = 0;
    };
}
