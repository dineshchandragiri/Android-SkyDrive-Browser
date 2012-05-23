//------------------------------------------------------------------------------
// Copyright (c) 2012 Microsoft Corporation. All rights reserved.
//
// Description: See the class level JavaDoc comments.
//------------------------------------------------------------------------------

package com.microsoft.live;

import org.json.JSONObject;

import android.text.TextUtils;

public class LiveOperation {

    static class Builder {
        private ApiRequestAsync<JSONObject> apiRequestAsync;
        private final String method;
        private final String path;
        private JSONObject result;
        private Object userState;

        public Builder(String method, String path) {
            assert !TextUtils.isEmpty(method);
            assert !TextUtils.isEmpty(path);

            this.method = method;
            this.path = path;
        }

        /**
         * Set if the operation to build is an async operation.
         *
         * @param apiRequestAsync
         * @return this Builder
         */
        public Builder apiRequestAsync(ApiRequestAsync<JSONObject> apiRequestAsync) {
            assert apiRequestAsync != null;

            this.apiRequestAsync = apiRequestAsync;
            return this;
        }

        public LiveOperation build() {
            return new LiveOperation(this);
        }

        public Builder result(JSONObject result) {
            assert result != null;
            this.result = result;
            return this;
        }

        public Builder userState(Object userState) {
            this.userState = userState;
            return this;
        }
    }

    private final ApiRequestAsync<JSONObject> apiRequestAsync;
    private final String method;
    private final String path;
    private JSONObject result;
    private final Object userState;

    private LiveOperation(Builder builder) {
        this.apiRequestAsync = builder.apiRequestAsync;
        this.method = builder.method;
        this.path = builder.path;
        this.result = builder.result;
        this.userState = builder.userState;
    }

    public void cancel() {
        final boolean isCancelable = this.apiRequestAsync != null;
        if (isCancelable) {
            this.apiRequestAsync.cancel(true);
        }
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getRawResult() {
        final JSONObject result = this.getResult();
        if (result == null) {
            return null;
        }

        return result.toString();
    }

    public JSONObject getResult() {
        return this.result;
    }

    public Object getUserState() {
        return this.userState;
    }

    void setResult(JSONObject result) {
        assert result != null;

        this.result = result;
    }
}
