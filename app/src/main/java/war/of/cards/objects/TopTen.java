package war.of.cards.objects;

import java.util.ArrayList;

import war.of.cards.utils.Constants;

public class TopTen {

    private ArrayList<Record> allRecords;

    public TopTen() {
        allRecords = new ArrayList<>();
    }

    public TopTen(ArrayList<Record> allRecords) {
        this.allRecords = allRecords;
    }

    public ArrayList<Record> getAllRecords() {
        return allRecords;
    }

    public void setAllRecords(ArrayList<Record> allRecords) {
        this.allRecords = allRecords;
    }

    public int addNewRecord(Record record) {
        int rank = -1;

        for (int i = 0; rank == -1 && i < Constants.MAX_SIZE && i < allRecords.size(); i++) {
            if (record.getScore() > allRecords.get(i).getScore()) {
                allRecords.add(i, record);
                rank = i + 1;
            }
        }
        if (rank == -1 && allRecords.size() < Constants.MAX_SIZE) {
            allRecords.add(record);
            rank = allRecords.size();
        } else if (allRecords.size() > Constants.MAX_SIZE) {
            allRecords.remove(Constants.MAX_SIZE);
        }

        return rank;
    }

}
