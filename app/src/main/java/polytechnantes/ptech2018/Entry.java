package polytechnantes.ptech2018;

public class Entry {
    public int entryID;
    public float value;
    public String date;

    public Entry(int ID, float entry, String entryDate)
    {
        entryID = ID;
        value = entry;
        date = new String(entryDate);
    }

    public int getEntryID()
    {
        return entryID;
    }

    public float getEntryValue()
    {
        return value;
    }

    public String getEntryDate()
    {
        return date;
    }
}
