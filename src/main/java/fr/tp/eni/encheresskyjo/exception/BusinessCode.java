package fr.tp.eni.encheresskyjo.exception;

public class BusinessCode {

    // USER
    public static final String USER_NOT_FOUND = "validation.user.not.found";
    public static final String VALID_USER = "validation.user";
    public static final String VALID_USER_UNIQUENESS = "validation.user.uniqueness";
    public static final String VALID_USER_USERNAME_BLANK = "validation.user.username.blank";
    public static final String VALID_USER_USERNAME_LENGTH_MAX = "validation.user.username.length.max";
    public static final String VALID_USER_USERNAME_UNIQUENESS = "validation.user.username.uniqueness";
    public static final String VALID_USER_FIRSTNAME_BLANK = "validation.user.firstname.blank";
    public static final String VALID_USER_FIRSTNAME_LENGTH_MAX = "validation.user.firstname.length.max";
    public static final String VALID_USER_LASTNAME_BLANK = "validation.user.lastname.blank";
    public static final String VALID_USER_LASTNAME_LENGTH_MAX= "validation.user.lastname.length.max";
    public static final String VALID_USER_EMAIL_BLANK= "validation.user.email.blank";
    public static final String VALID_USER_EMAIL_FORMAT = "validation.user.email.format";
    public static final String VALID_USER_EMAIL_LENGTH_MAX = "validation.user.email.length.max";
    public static final String VALID_USER_EMAIL_UNIQUENESS = "validation.user.email.uniqueness";
    public static final String VALID_USER_PHONE_FORMAT = "validation.user.phone.format";
    public static final String VALID_USER_PHONE_LENGTH_MAX = "validation.user.phone.length.max";
    public static final String VALID_ADDRESS_STREET_NAME_BLANK = "validation.address.street.blank";
    public static final String VALID_ADDRESS_STREET_NAME_LENGTH_MAX = "validation.address.street.length.max";
    public static final String VALID_ADDRESS_STREET_NAME_FORMAT = "validation.address.street.format";
    public static final String VALID_ADDRESS_ZIP_BLANK = "validation.address.zip.blank";
    public static final String VALID_ADDRESS_ZIP_LENGTH_MAX = "validation.address.zip.length.max";
    public static final String VALID_ADDRESS_ZIP_FORMAT = "validation.address.zip.format";
    public static final String VALID_ADDRESS_CITY_BLANK = "validation.address.city.blank";
    public static final String VALID_ADDRESS_CITY_LENGTH_MAX = "validation.address.city.length.max";
    public static final String VALID_USER_PASSWORD_BLANK= "validation.user.password.blank";
    public static final String VALID_USER_PASSWORD_FORMAT = "validation.user.password.format";
    public static final String VALID_USER_PASSWORD_CONFIRM= "validation.user.password.confirm";
    public static final String VALID_USER_PASSWORD_CURRENT_BLANK = "validation.user.password.current.blank";
    public static final String VALID_USER_CURRENT_PASSWORD = "validation.user.current.password";


    // ARTICLE
    public static final String VALID_ARTICLE = "validation.article";
    public static final String VALID_ARTICLE_NAME_BLANK = "validation.article.name.blank";
    public static final String VALID_ARTICLE_NAME_LENGTH_MAX = "validation.article.name.length.max";
    public static final String VALID_ARTICLE_NAME_LENGTH_MIN = "validation.article.name.length.min";
    public static final String VALID_ARTICLE_DESCRIPTION_BLANK = "validation.article.description.blank";
    public static final String VALID_ARTICLE_DESCRIPTION_LENGTH_MAX = "validation.article.description.length.max";
    public static final String VALID_ARTICLE_DESCRIPTION_LENGTH_MIN = "validation.article.description.length.min";
    public static final String VALID_ARTICLE_STARTDATE_NULL = "validation.article.startdate.null";
    public static final String VALID_ARTICLE_STARTDATE_BEFORE = "validation.article.startdate.before";
    public static final String VALID_ARTICLE_ENDDATE_NULL = "validation.article.enddate.null";
    public static final String VALID_ARTICLE_ENDDATE_BEFORE = "validation.article.enddate.before";
    public static final String VALID_ARTICLE_CATEGORY_NULL = "validation.article.category.null";
    public static final String VALID_ARTICLE_CATEGORY_UNKNOWN_ID = "validation.article.category.unknown.id";
    public static final String VALID_ARTICLE_STARTINGPRICE_MIN = "validation.article.startingprice.min";
    public static final String VALID_ARTICLE_IMAGEURL_PATTERN = "validation.article.imageurl.pattern";
    public static final String VALID_ARTICLE_PICKUP_NULL = "validation.article.pickup.null";
    //public static final String VALID_ARTICLE_PICKUP_UNKNOWN_ARTICLE_ID = "validation.article.pickup.unknown.article.id";
    public static final String VALID_ARTICLE_UNIQUE = "validation.article.unique";
    public static final String CANCEL_SALE_UNVALID_USER = "cancel.sale.unvalid.user";
    public static final String CANCEL_SALE_ALREADY_ENDED = "cancel.sale.already.ended";

    //BID
    public static final String VALID_BID_USER_NULL = "validation.bid.user.null";
    public static final String VALID_BID_USER_CREDIT_SCARCE="validation.bid.user.credit.scarce";
    public static final String VALID_BID_ARTICLE_NULL = "validation.bid.article.null";
    public static final String VALID_BID_PRICE_LOWER_BEST_BID = "validation.bid.price.lower.best.bid";
    public static final String VALID_BID_PRICE_LOWER_STARTING_PRICE = "validation.bid.price.lower.start.price";
    public static final String VALID_BID_DATE_BEFORE_START = "validation.bid.date.before.start";
    public static final String VALID_BID_DATE_AFTER_END = "validation.bid.date.after.end";


}
