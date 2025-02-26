package com.material.tortoise.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Goals::class,GoalHistory::class],
    version = 4,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class TortoiseDatabase : RoomDatabase() {

    abstract fun tortoiseDao(): TortoiseDao

    companion object {

        @Volatile
        private var INSTANCE: TortoiseDatabase? = null



        fun getDatabase(context: Context): TortoiseDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): TortoiseDatabase {

            return Room.databaseBuilder(
                context.applicationContext,
                TortoiseDatabase::class.java,
                "tortoise_database"
            ).addMigrations(MIGRATION_3_4).build()
        }
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `goalHistory` (`id` INTEGER , `goalName` TEXT, `statusFlag` INTEGER,'stepsCount' INTEGER,'targetedSteps' INTEGER,'dateCreated' INTEGER,'updatedDate' INTEGER , PRIMARY KEY(`id`))")
            }
        }
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE IF EXISTS `goalHistory`")
                database.execSQL("CREATE TABLE IF NOT EXISTS `goalHistory` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `goalName` TEXT, `statusFlag` INTEGER,'stepsCount' INTEGER,'targetedSteps' INTEGER,'dateCreated' INTEGER,'updatedDate' INTEGER)")
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE `goalHistory` ADD `dateInString` TEXT")
            }
        }
    }

}

