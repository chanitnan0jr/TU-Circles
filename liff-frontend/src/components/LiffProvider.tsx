"use client";

import React, { createContext, useContext, useEffect, useState } from "react";
import type { Liff } from "@line/liff";

interface LiffContextType {
  liff: Liff | null;
  isInitialized: boolean;
  isLoggedIn: boolean;
  error: string | null;
}

const LiffContext = createContext<LiffContextType>({
  liff: null,
  isInitialized: false,
  isLoggedIn: false,
  error: null,
});

export const useLiff = () => useContext(LiffContext);

export function LiffProvider({ children }: { children: React.ReactNode }) {
  const [liffObject, setLiffObject] = useState<Liff | null>(null);
  const [isInitialized, setIsInitialized] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let isMounted = true;
    const liffId = process.env.NEXT_PUBLIC_LIFF_ID;

    const initLiff = async () => {
      if (!liffId) {
        if (isMounted) {
          setError("NEXT_PUBLIC_LIFF_ID is not set in environment variables.");
          setIsInitialized(true);
        }
        return;
      }

      try {
        const liffModule = await import("@line/liff");
        const liff = liffModule.default;
        await liff.init({ liffId });
        if (isMounted) {
          setLiffObject(liff);
          setIsLoggedIn(liff.isLoggedIn());
          setIsInitialized(true);
        }
      } catch (err: unknown) {
        console.error("LIFF initialization failed:", err);
        if (isMounted) {
          const message =
            err instanceof Error
              ? err.message
              : "Failed to initialize LINE LIFF.";
          setError(message);
          setIsInitialized(true);
        }
      }
    };

    initLiff();

    return () => {
      isMounted = false;
    };
  }, []);

  return (
    <LiffContext.Provider
      value={{ liff: liffObject, isInitialized, isLoggedIn, error }}
    >
      {error ? (
        <div className="flex min-h-screen flex-col items-center justify-center p-6 text-center bg-slate-50 text-slate-800">
          <div className="max-w-md rounded-2xl bg-white p-8 shadow-lg border border-slate-200">
            <h1 className="text-xl font-bold text-red-600 mb-2">
              กรุณาเปิดผ่าน LINE
            </h1>
            <p className="text-sm text-slate-600 mb-4">{error}</p>
            <p className="text-xs text-slate-400">
              TU Circles MINI APP requires LINE LIFF Environment.
            </p>
          </div>
        </div>
      ) : (
        children
      )}
    </LiffContext.Provider>
  );
}
