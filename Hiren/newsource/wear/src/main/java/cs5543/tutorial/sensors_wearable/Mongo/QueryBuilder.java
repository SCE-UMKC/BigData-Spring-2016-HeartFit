package cs5543.tutorial.sensors_wearable.Mongo;
import cs5543.tutorial.sensors_wearable.MyContact;

public class QueryBuilder {


    public String getDatabaseName() {

        return "labwork";
    }


    public String getApiKey() {

        return "hxRIlF7p7mAyipFbwWfI3-SCHx6jd7Vv";
    }

    /**
     * This constructs the URL that allows you to manage your database,
     * collections and documents
     * @return
     */
    public String getBaseUrl()
    {
        return "https://api.mongolab.com/api/1/databases/"+getDatabaseName()+"/collections/";
    }

    /**
     * Completes the formating of your URL and adds your API key at the end
     * @return
     */
    public String docApiKeyUrl()
    {

        return "?apiKey="+getApiKey();
    }

    /**
     * Returns the docs101 collection
     * @return
     */
    public String documentRequest()
    {
        return "Heartrate";
    }

    /**
     * Builds a complete URL using the methods specified above
     * @return
     */
    public String buildContactsSaveURL()
    {

        return getBaseUrl()+documentRequest()+docApiKeyUrl();
    }

    /**
     * Formats the contact details for MongoHQ Posting
     * @param contact: Details of the person
     * @return
     */
    public String createContact(MyContact contact)
    {
        return String
                .format("{\"document\"  : {\"heartrate\": \"%s\", "
                                + "\"stepcount\": \"%s\"}",
                        contact.heartrate, contact.stepcount);
    }



}