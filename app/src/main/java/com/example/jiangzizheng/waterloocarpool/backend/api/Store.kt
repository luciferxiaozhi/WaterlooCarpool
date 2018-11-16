package com.example.jiangzizheng.waterloocarpool.backend.api

import com.example.jiangzizheng.waterloocarpool.backend.api.TaskDecorators.withFailureLog
import com.example.jiangzizheng.waterloocarpool.backend.bean.Trip
import com.example.jiangzizheng.waterloocarpool.backend.bean.User
import com.google.android.gms.tasks.Task
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
        private fun takeCollection(userId: String): CollectionReference? {
            val user = Auth.instance.currentUser ?: return null
            return instance.collection("users")
        }

        private fun takeDocument(userId: String, tripId: String): DocumentReference? {
            return takeCollection(userId)?.document(tripId)
        }

        fun fetch(userId: String, tripId: String): Task<Trip>? {
            return takeDocument(userId, tripId)
                    ?.get()
                    ?.continueWith {it.result?.toObject(Trip::class.java)!! }
                ?.withFailureLog("FireStore")
        }

        fun fetchAll(userId: String): Task<LinkedHashMap<String, Trip>>? {
            return takeCollection(userId)
                ?.get()
                ?.continueWith { task ->
                    LinkedHashMap(task.result?.associate { trip ->
                        trip.id to trip.toObject(Trip::class.java)
                    })
                }
                ?.withFailureLog("FireStore")
        }

        fun add(userId: String, trip: Trip): Task<DocumentReference>? {
            return takeCollection(userId)
                ?.add(trip)
                ?.withFailureLog("FireStore")
        }

        fun update(userId: String, tripId: String, data: Map<String, Any>): Task<Void>? {
            return takeDocument(userId, tripId)
                ?.set(data, SetOptions.merge())
                ?.withFailureLog("FireStore")
        }

        fun delete(userId: String, tripId: String): Task<Void>? {
            return takeDocument(userId, tripId)
                ?.delete()
                ?.withFailureLog("FireStore")
        }
    }
}