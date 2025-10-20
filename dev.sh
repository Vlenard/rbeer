#!/bin/bash

FRONTEND_PATH="./frontend/"
PUBLIC_PATH="./public/"
VIEWS_PATH="./views/"
EXCLUDE_DIRS=("app") # Directories to exclude

usage(){
    echo "Usage: $0 [--frontend] [--backend] [--all]"
    echo "  --frontend      Start only frontend (dev)"
    echo "  --backend       Start backend (dev) and auto-sync UI files"
    echo "  --all           Start both frontend and backend (dev)"
    exit 1
}

copy_frontend_assets(){

    echo "🔄 Syncing frontend assets..."

    if [ -f "tailwind.config.js" ]; then
        echo "Compile tailwind"
        npx @tailwindcss/cli -i "$FRONTEND_PATH/input.css" -o "$PUBLIC_PATH/output.css" --minify
    fi

    # Build exclusion parameters
    EXCLUDES=()
    for dir in "${EXCLUDE_DIRS[@]}"; do
        EXCLUDES+=(--exclude="$dir")
    done

    EXCLUDES+=(--exclude="input.css")

    mkdir -p "$PUBLIC_PATH" "$VIEWS_PATH"

    # Copy .js and .css → backend/public
    rsync -a --prune-empty-dirs "${EXCLUDES[@]}" \
        --include='*/' \
        --include='*.js' \
        --include='*.css' \
        --exclude='*' \
        "$FRONTEND_PATH" "$PUBLIC_PATH" >/dev/null

    # Copy .handlebars → backend/views
    rsync -a --prune-empty-dirs "${EXCLUDES[@]}" \
        --include='*/' \
        --include='*.handlebars' \
        --exclude='*' \
        "$FRONTEND_PATH" "$VIEWS_PATH" >/dev/null

    echo "✅ Synced at $(date +'%H:%M:%S')"
}

watch_frontend(){
    echo "👀 Watching frontend for file changes..."
    copy_frontend_assets

    if command -v inotifywait >/dev/null 2>&1; then
        # Linux / WSL
        while true; do
            inotifywait -r -e modify,create,delete,move \
                --exclude 'node_modules|dist|tmp|tests' \
                "$FRONTEND_PATH" >/dev/null 2>&1
            copy_frontend_assets
        done
    elif command -v fswatch >/dev/null 2>&1; then
        # macOS
        fswatch -r -e "node_modules" -e "dist" -e "tmp" -e "tests" "$FRONTEND_PATH" | while read; do
            copy_frontend_assets
        done
    else
        echo "⚠️  File watcher not found. Please install one of the following:"
        echo "   • Linux: sudo apt install inotify-tools"
        echo "   • macOS: brew install fswatch"
        echo "Watching disabled."
    fi
}

if [ $# -eq 0 ]; then
    usage
fi

for arg in "$@"; do
    case $arg in
        --frontend)
            cd frontend/app || exit
            npm run dev
        ;;
        --backend)
            # Run watcher in background
            watch_frontend &
            tsnd --respawn src/index.ts
        ;;
        --all)
            # Run watcher and frontend concurrently
            watch_frontend &
            (cd frontend/app && npm run dev &) 
            tsnd --respawn src/index.ts
        ;;
        *)
            usage
        ;;
    esac
done
