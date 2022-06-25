# Add hook:
# echo '. "$(git rev-parse --show-toplevel)/format-staged.sh"' > .git/hooks/pre-commit && chmod +x .git/hooks/pre-commit

echo "Formatting staged files..."

FILES=$(                                                                \
    git diff                                                            \
        --cached                                                        \
        --name-only                                                     \
        --line-prefix=$(git rev-parse --show-toplevel)/                 \
        --diff-filter=ACMR                                              \
      | grep '\.java$'                                                  \
      | sed 's| |\\ |g'                                                 \
)
[ -z "$FILES" ] && exit 0


COMMA_SEPARATED=$(echo "$FILES" | sed -z 's/\n/,/g;s/,$//')
./mvnw spotless:apply -DspotlessFiles="$COMMA_SEPARATED"

echo "$FILES" | xargs git add

if [ ! "`git diff --cached --name-only`" ]; then
    1>&2 echo "No files left after formatting"
    exit 1
fi
