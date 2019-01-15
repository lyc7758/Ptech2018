package polytechnantes.ptech2018;

public class Channel {
    private Field fields[];
    private String channelName;

    public Channel(String name, int nbOfFields)
    {
        int i;

        channelName = new String(name);
        fields = new Field[nbOfFields];

        for(i=1;i<=nbOfFields;i++)
        {
            fields[i-1] = new Field("field"+String.valueOf(i));
        }
    }
    public Field[] getFields(){
        return fields;
    }
    public String getChannelName()
    {
        return channelName;
    }

    public String getFieldNameByNb(int fieldNb)
    {
        return fields[fieldNb-1].getFieldName();
    }

    public Field getFieldByName(String name)
    {
        for(int i=0;i<fields.length;i++)
        {
            if(name.contentEquals(this.getFieldNameByNb(i+1)))
            {
                return fields[i];
            }
        }
        return null;
    }

    public void addEntry(String fieldName, int ID, float entry, String entryDate)
    {
        for(int i=0;i<fields.length;i++)
        {
            if(fieldName.contentEquals(this.getFieldNameByNb(i+1)))
            {
                fields[i].addEntry(ID, entry, entryDate);
                break;
            }
        }
    }
    public void addEntry(int fieldid, int ID, Double entry, String entryDate)
    {
        if(entry != null){
        double entry1 = entry;
        float entry2 = (float)entry1;
        fields[fieldid].addEntry(ID, entry2, entryDate);
        }
    }
}
