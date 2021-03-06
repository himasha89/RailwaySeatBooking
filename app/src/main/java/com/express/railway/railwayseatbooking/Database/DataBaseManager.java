package com.express.railway.railwayseatbooking.Database;

import android.content.Context;
import android.os.AsyncTask;

import com.express.railway.railwayseatbooking.Database.Dao.JourneyDao;
import com.express.railway.railwayseatbooking.Database.Dao.ReservationDao;
import com.express.railway.railwayseatbooking.Database.Dao.SeatDao;
import com.express.railway.railwayseatbooking.Database.Dao.TrainDao;
import com.express.railway.railwayseatbooking.Model.Journey;
import com.express.railway.railwayseatbooking.Model.Seat;
import com.express.railway.railwayseatbooking.Model.Train;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DataBaseManager {

    private static JourneyDao journeyDao;
    private static TrainDao trainDao;
    private static SeatDao seatDao;
    private static ReservationDao reservationDao;


    public DataBaseManager(Context context) {
        journeyDao = DatabaseHolder.getDatabaseInstance(context).journeyDao();
        trainDao = DatabaseHolder.getDatabaseInstance(context).trainDao();
        seatDao = DatabaseHolder.getDatabaseInstance(context).seatDao();
        reservationDao = DatabaseHolder.getDatabaseInstance(context).reservationDao();
    }

    public void SaveTrainAndSeatToDatabase(final Train train, final Seat seat) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                trainDao.save(train);
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("TRAIN NUM IS "+train.getTrainNo()+" "+train.getTrainName());
                seat.setTrainNo(train.getTrainNo());
                seatDao.save(seat);
            }
        });
    }

    public void saveDataToDataBase(ArrayList<Journey> arrayList) {
        SaveDataToDatabase saveDataToDatabase = new SaveDataToDatabase( arrayList);
        saveDataToDatabase.execute();
    }

    public void saveItemToDataBase(Journey entry) {
        SaveItemToDatabase saveItemToDatabase = new SaveItemToDatabase( entry);
        saveItemToDatabase.execute();
    }

    public void SaveTrainToDatabase(Train entry) {
        SaveTrainToDatabase saveItemToDatabase = new SaveTrainToDatabase(entry);
        saveItemToDatabase.execute();
    }

    public void SaveSeatToDatabase(Seat entry) {
        SaveSeatToDatabase saveItemToDatabase = new SaveSeatToDatabase(entry);
        saveItemToDatabase.execute();
    }

    public void SaveJourneyToDatabase(Journey entry) {
        SaveJourneyToDatabase saveItemToDatabase = new SaveJourneyToDatabase(entry);
        saveItemToDatabase.execute();
    }


    public void remove(Journey journey) {
        RemoveItemFromDataBase removeItemFromDataBase = new RemoveItemFromDataBase(journey);
        removeItemFromDataBase.execute();
    }


    public int listSize() {
        return journeyDao.getNumberOfRows();
    }


    public ArrayList<Journey> getData() {
        retrievedDataFromDatabase retrievedDataFromDatabase =
                new retrievedDataFromDatabase();
        try {
            ArrayList<Journey> es = retrievedDataFromDatabase.execute().get();
            return es;
        } catch (InterruptedException | ExecutionException e) {
            return new ArrayList<>();
        }
    }

    public ArrayList<Train> getTrainData() {
        retrievedTrainDataFromDatabase retrievedTrainDataFromDatabase =
                new retrievedTrainDataFromDatabase();
        try {
            ArrayList<Train> es = retrievedTrainDataFromDatabase.execute().get();
            return es;
        } catch (InterruptedException | ExecutionException e) {
            return new ArrayList<>();
        }
    }

    public ArrayList<Seat> getSeatData() {
        retrievedSeatDataFromDatabase retrievedSeatDataFromDatabase =
                new retrievedSeatDataFromDatabase();
        try {
            ArrayList<Seat> es = retrievedSeatDataFromDatabase.execute().get();
            return es;
        } catch (InterruptedException | ExecutionException e) {
            return new ArrayList<>();
        }
    }

    public void removeAll() {
        journeyDao.clearAll();
    }

    private static class SaveDataToDatabase extends AsyncTask<Void, Void, Void> {


        private ArrayList<Journey> journeyArrayList;


        SaveDataToDatabase( ArrayList<Journey> entry) {

            this.journeyArrayList = entry;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            journeyDao.saveAll(journeyArrayList);
            return null;

        }
    }

    private static class SaveItemToDatabase extends AsyncTask<Void, Void, Void> {

        private Journey journey;


        SaveItemToDatabase(Journey journey) {
            this.journey = journey;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            journeyDao.save(journey);
            return null;

        }
    }

    private static class RemoveItemFromDataBase extends AsyncTask<Void, Void, Void> {

        private Journey journey;


        RemoveItemFromDataBase(Journey flower) {
            this.journey = flower;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            journeyDao.delete(journey);
            return null;
        }
    }

    private static class retrievedDataFromDatabase extends AsyncTask<Void, Void, ArrayList<Journey>> {

        @Override
        protected ArrayList<Journey> doInBackground(Void... voids) {
            return (ArrayList<Journey>) journeyDao.getAll();
        }

        @Override
        protected void onPostExecute(ArrayList<Journey> es) {
            super.onPostExecute(es);
        }
    }

    private static class retrievedTrainDataFromDatabase extends AsyncTask<Void, Void, ArrayList<Train>> {

        @Override
        protected ArrayList<Train> doInBackground(Void... voids) {
            return (ArrayList<Train>) trainDao.getAll();
        }

        @Override
        protected void onPostExecute(ArrayList<Train> es) {
            super.onPostExecute(es);
        }
    }

    private static class retrievedSeatDataFromDatabase extends AsyncTask<Void, Void, ArrayList<Seat>> {

        @Override
        protected ArrayList<Seat> doInBackground(Void... voids) {
            return (ArrayList<Seat>) seatDao.getAll();
        }

        @Override
        protected void onPostExecute(ArrayList<Seat> es) {
            super.onPostExecute(es);
        }
    }

    private static class SaveTrainAndSeatToDatabase extends AsyncTask<Void, Void, Seat> {

        private Train train;
        private Seat seat;

        SaveTrainAndSeatToDatabase(Train train, Seat seat) {
            this.train = train;
            this.seat = seat;
        }

        @Override
        protected Seat doInBackground(Void... voids) {
            trainDao.save(train);
            return null;

        }

        @Override
        protected void onPostExecute(Seat seats) {
            super.onPostExecute(seats);
            seatDao.save(seat);
        }
    }

    private static class SaveTrainToDatabase extends AsyncTask<Void, Void, Void> {

        private Train train;


        SaveTrainToDatabase(Train train) {
            this.train = train;
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            train.setTrainNo(456);
            trainDao.save(train);
            return null;

        }
    }

    private static class SaveSeatToDatabase extends AsyncTask<Void, Void, Void> {

        private Seat seat;


        SaveSeatToDatabase(Seat seat) {
            this.seat = seat;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            seatDao.save(seat);
            return null;

        }
    }

    private static class SaveJourneyToDatabase extends AsyncTask<Void, Void, Void> {

        private Journey journey;


        SaveJourneyToDatabase(Journey journey) {
            this.journey = journey;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            journeyDao.save(journey);
            return null;

        }
    }
}
