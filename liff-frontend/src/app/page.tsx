"use client";

import { motion } from "motion/react";
import { useLiff } from "@/components/LiffProvider";

export default function Home() {
  const { isInitialized, isLoggedIn, liff } = useLiff();

  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-6 bg-gradient-to-br from-red-50 to-amber-50">
      <motion.div
        initial={{ opacity: 0, y: 20 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 0.5 }}
        className="w-full max-w-md rounded-2xl bg-white p-6 shadow-xl border border-red-100 text-center"
      >
        <div className="mb-4 inline-flex h-16 w-16 items-center justify-center rounded-full bg-red-100 text-red-600 font-bold text-2xl">
          TU
        </div>
        <h1 className="text-2xl font-bold text-slate-900 mb-2">TU Circles</h1>
        <p className="text-sm text-slate-600 mb-6">
          Thammasat University Community & Open Call Platform
        </p>

        <div className="rounded-xl bg-slate-50 p-4 border border-slate-100 text-left text-sm text-slate-700 space-y-2">
          <div className="flex justify-between items-center">
            <span className="font-medium text-slate-500">LIFF Status</span>
            <span
              className={`px-2 py-0.5 rounded-full text-xs font-semibold ${
                isInitialized
                  ? "bg-emerald-100 text-emerald-800"
                  : "bg-amber-100 text-amber-800"
              }`}
            >
              {isInitialized ? "Initialized" : "Loading..."}
            </span>
          </div>

          <div className="flex justify-between items-center">
            <span className="font-medium text-slate-500">LINE Auth</span>
            <span
              className={`px-2 py-0.5 rounded-full text-xs font-semibold ${
                isLoggedIn
                  ? "bg-emerald-100 text-emerald-800"
                  : "bg-slate-200 text-slate-700"
              }`}
            >
              {isLoggedIn ? "Logged In" : "Not Logged In"}
            </span>
          </div>
        </div>

        {isInitialized && !isLoggedIn && liff && (
          <button
            onClick={() => liff.login()}
            className="mt-6 w-full rounded-xl bg-emerald-600 px-4 py-3 font-semibold text-white transition hover:bg-emerald-700 shadow-md active:scale-95"
          >
            Log in with LINE
          </button>
        )}
      </motion.div>
    </main>
  );
}
