package br.com.cpqd.instrumentacao.sige.typedef;

public final class intents {
    public static final String  CATEGORY_ANSWER_SESSION             = "br.com.cpqd.answer.category.SESSION";
    public static final String  CATEGORY_SESSION                    = "br.com.cpqd.ask.category.SESSION";

    public final class login {
        public final class ask{
            public static final String  ACTION_LOGIN        = "br.com.cpqd.ask.action.LOGIN";
            public static final String  PARAM_USERNAME      = "br.com.cpqd.ask.param.USERNAME";
            public static final String  PARAM_PASSWORD      = "br.com.cpqd.ask.param.PASSWORD";
        }
        public final class answer{

            public static final String  ACTION_SUCCESSFUL_LOGIN             = "br.com.cpqd.answer.action.SUCCESSFULLY_LOGIN";
            public static final String  ACTION_FAILED_LOGIN                 = "br.com.cpqd.answer.action.FAILED_LOGIN";
            public static final String  PARAM_USERNAME                      = "br.com.cpqd.answer.param.USERNAME";
            public static final String  PARAM_LABORATORY                    = "br.com.cpqd.answer.param.LABORATORY";
            public static final String  PARAM_LOGIN_ERROR                   = "br.com.cpqd.answer.param.LOGIN_ERROR";
        }

    }
    public final class data_receive {
        public final class ask{

        }
        public final class answer{

            public static final String  ACTION_SUCCESSFUL_DATA_RECEIVED     = "br.com.cpqd.answer.action.SUCCESSFULLY_DATA_RECEIVED";
            public static final String  ACTION_FAILED_DATA_RECEIVED         = "br.com.cpqd.answer.action.FAILED_DATA_RECEIVED";
        }

    }
    public final class logout {
        public final class ask{
            public static final String  ACTION_LOGOUT       = "br.com.cpqd.ask.action.LOGOUT";
        }
        public final class answer{
            public static final String  ACTION_SUCCESSFUL_LOGOUT            = "br.com.cpqd.answer.action.SUCCESSFULLY_LOGOUT";
            public static final String  ACTION_FAILED_LOGOUT                = "br.com.cpqd.answer.action.FAILED_LOGOUT";
        }

    }
    public final class scanner{
        public static final String  ACTION_NEW_DATA                 = "br.com.cpqd.scanner.action.NEW_DATA";
        public static final String  CATEGORY_SCANNER                = "br.com.cpqd.scanner.category.SCANNER";
    }
}
