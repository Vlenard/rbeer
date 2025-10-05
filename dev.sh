#!/bin/bash

FRONTEND_PATH="frontend/"
PUBLIC_PATH="public/"
VIEWS_PATH="views/"
EXCLUDE_DIRS=("app") # Directories to exclude from copying

usage(){
    echo "Usage: $0 [--frontend] [--backend] [--all]"
    echo "  --frontend      Start only frontend (dev)"
    echo "  --backend       Start only backend (dev) and copy UI files"
    echo "  --all           Start both frontend and backend (dev)"
    exit 1
}

copy_frontend_assets(){
    echo "Copying frontend assets to backend..."

    # Build exclusion parameters
    EXCLUDES=()
    for dir in "${EXCLUDE_DIRS[@]}"; do
        EXCLUDES+=(--exclude="$dir")
    done

    mkdir -p "$PUBLIC_PATH" "$VIEWS_PATH"

    # Copy .js and .css → public/
    rsync -av --prune-empty-dirs "${EXCLUDES[@]}" \
        --include='*/' \
        --include='*.js' \
        --include='*.css' \
        --exclude='*' \
        "$FRONTEND_PATH" "$PUBLIC_PATH"

    # Copy .handlebars → views/
    rsync -av --prune-empty-dirs "${EXCLUDES[@]}" \
        --include='*/' \
        --include='*.handlebars' \
        --exclude='*' \
        "$FRONTEND_PATH" "$VIEWS_PATH"

    echo "Frontend assets copied successfully."
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
            copy_frontend_assets
            tsnd --respawn src/index.ts
        ;;
        --all)
            copy_frontend_assets
            (cd frontend/app && npm run dev &)  # Run frontend in background
            tsnd --respawn src/index.ts
        ;;
        *)
            usage
        ;;
    esac
done
