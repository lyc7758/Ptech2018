package polytechnantes.ptech2018;



public class Field {
    private String fieldName;
    private Entry entries[];
    private int numberOfEntries;
    private static final int maxEntries = 8000;

    public Field(String name)
    {
        fieldName = new String(name);
        entries = new Entry[maxEntries];
        numberOfEntries = 0;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public int getLastEntryID()
    {
        return numberOfEntries;
    }

    public Entry getLastEntry()
    {
        return entries[numberOfEntries-1];
    }

    public int addEntry(int ID, float entry, String entryDate)
    {
        if(numberOfEntries + 1 != maxEntries)
        {
            entries[numberOfEntries] = new Entry(ID, entry, entryDate);
            numberOfEntries ++;
            return 0;
        }
        else
        {
            return -1;
        }
    }

    public Entry[] getEntries()
    {
        return entries;
    }
}
