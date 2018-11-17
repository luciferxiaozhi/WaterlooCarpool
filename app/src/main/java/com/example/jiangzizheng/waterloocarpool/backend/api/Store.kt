package com.example.jiangzizheng.waterloocarpool.backend.api

import com.example.jiangzizheng.waterloocarpool.backend.api.TaskDecorators.withFailureLog
import com.example.jiangzizheng.waterloocarpool.backend.bean.Trip
import com.example.jiangzizheng.waterloocarpool.backend.bean.User
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.*

object Store {
    val instance by lazy {
        FirebaseFirestore.getInstance().apply {
            firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build()
        }
    }

    object UserCollection {
        private fun takeCollection(): CollectionReference? {
            val user = Auth.instance.currentUser ?: return null
            return instance.collection("users/")
        }

        private fun takeDocument(User: String): DocumentReference? {
            return takeCollection()?.document(User)
        }

        fun fetch(userId: String): Task<User>? {
            return takeDocument(userId)
                ?.get()
                ?.continueWith { it.result?.toObject(User::class.java)!! }
                ?.withFailureLog("FireStore")
        }

        fun fetchAll(): Task<LinkedHashMap<String, User>>? {
            return takeCollection()
                ?.get()
                ?.continueWith { task ->
                    LinkedHashMap(task.result?.associate { user ->
                        user.id to user.toObject(User::class.java)
                    })
                }
                ?.withFailureLog("FireStore")
        }

        fun add(user: User): Task<DocumentReference>? {
            return takeCollection()
                ?.add(user)
                ?.withFailureLog("FireStore")
        }

        fun update(userId: String, data: Map<String, Any>): Task<Void>? {
            return takeDocument(userId)
                ?.set(data, SetOptions.merge())
                ?.withFailureLog("FireStore")
        }

        fun delete(userId: String): Task<Void>? {
            return takeDocument(userId)
                ?.delete()
                ?.withFailureLog("FireStore")
        }
    }

    object TripCollection {
        private fun takeCollection(): CollectionReference? {
            // separate trips and users apart to make it easier to operate
            return instance.collection("trips/")
        }

        private fun takeDocument(tripId: String): DocumentReference? {
            return takeCollection()?.document(tripId)
        }

        fun fetch(tripId: String): Task<Trip>? {
            return takeDocument(tripId)
                ?.get()
                ?.continueWith { it.result?.toObject(Trip::class.java)!! }
                ?.withFailureLog("FireStore")
        }

        fun fetchAll(): Task<LinkedHashMap<String, Trip>>? {
            return takeCollection()
                ?.get()
                ?.continueWith { task ->
                    LinkedHashMap(task.result?.associate { trip ->
                        trip.id to trip.toObject(Trip::class.java)
                    })
                }
                ?.withFailureLog("FireStore")
        }

        fun search(dCity: String, aCity: String, dDate: Timestamp, vacancies: Int): Task<LinkedHashMap<String, Trip>>? {
            return takeCollection()
                ?.whereEqualTo("dCity", dCity)
                ?.whereEqualTo("aCity", aCity)
                ?.whereGreaterThanOrEqualTo("vacancies", vacancies)
                ?.get()
                ?.continueWith { task ->
                    val start = dDate.seconds
                    val end = start + 86400
                    LinkedHashMap(task.result?.associate { trip ->
                        trip.id to trip.toObject(Trip::class.java)
                    }?.filter {
                        it.value.dDate.seconds in start..end
                    })
                }?.withFailureLog("FireStore")
        }

        fun add(trip: Trip): Task<DocumentReference>? {
            return takeCollection()
                ?.add(trip)
                ?.withFailureLog("FireStore")
        }

        fun update(tripId: String, data: Map<String, Any>): Task<Void>? {
            return takeDocument(tripId)
                ?.set(data, SetOptions.merge())
                ?.withFailureLog("FireStore")
        }

        fun delete(tripId: String): Task<Void>? {
            return takeDocument(tripId)
                ?.delete()
                ?.withFailureLog("FireStore")
        }
    }
}